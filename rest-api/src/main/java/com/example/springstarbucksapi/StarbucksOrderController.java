package com.example.springstarbucksapi;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;


@RestController
public class StarbucksOrderController {
    private final StarbucksOrderRepository repo;

    @Autowired
    private StarbucksCardRepository cardRepo;

    private HashMap<String, StarbucksOrder> activeOrders = new HashMap<>();

    public StarbucksOrderController(StarbucksOrderRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/order/register/{regid}") // currently, accepts any register id
    public StarbucksOrder createOrder(@PathVariable String regid, @RequestBody StarbucksOrder order) {
        System.out.println("Placing an order at Register " + regid + " => " + order);
        if (order.getDrink().equals("") || order.getMilk().equals("") | order.getSize().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order request.");
        }

        StarbucksOrder activeOrder = this.activeOrders.get(regid);
        if (activeOrder != null) {
            System.out.println("Active order at Register " + regid + " => " + activeOrder);
            if (activeOrder.getStatus().equals("Ready for payment!")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An active order already exists.");
            }
        }

        order.setTotal();
        order.setStatus("Ready for payment!");

        this.activeOrders.put(regid, order);

        return repo.save(order);
    }

    @GetMapping("/order/register/{regid}")
    public StarbucksOrder getOrder(@PathVariable String regid) {
        StarbucksOrder activeOrder = this.activeOrders.get(regid);
        
        if (activeOrder != null) {
            return activeOrder;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no active order at this register.");
        }
    }

    @DeleteMapping("/order/register/{regid}")
    public Status deleteOrder(@PathVariable String regid) {
        StarbucksOrder activeOrder = this.activeOrders.get(regid);

        if (activeOrder != null)  {
            repo.delete(activeOrder);
            this.activeOrders.remove(regid);

            Status response = new Status();
            response.setStatus("Active order cleared!");

            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no active order at this register to delete.");
        }
    }

    @PostMapping("/order/register/{regid}/pay/{cardNum}")
    public StarbucksCard payForOrder(@PathVariable String regid, @PathVariable String cardNum) {
        System.out.println("Paying at Register " + regid + " using Card " + cardNum);

        // check for an active order,
        StarbucksOrder activeOrder = this.activeOrders.get(regid);
        if (activeOrder == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found!");
        }

        // then, check for valid card
        if (cardNum.equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number not provided!");
        }

        // then, check if active order is already paid for,
        if (activeOrder.getStatus().startsWith("Paid with Card")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clear the paid, active order first!");
        }

        // if all is good, then find the appropriate card if possible,
        StarbucksCard card = cardRepo.findByCardNumber(cardNum);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found!");
        }

        // then, check if the card is activated
        if (!card.isActivated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card not activated!");
        }

        // if so, then check if the order can be paid for in full
        if (card.getBalance() - activeOrder.getTotal() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds on card!");
        }

        // if it can, then set the new balance on the card.
        card.setBalance(card.getBalance() - activeOrder.getTotal());
        cardRepo.save(card);

        activeOrder.setStatus("Paid with Card: " + cardNum + ", Remaining Balance: " + card.getBalance());
        repo.save(activeOrder);

        return card;
    }


    @GetMapping("/orders")
    public HashMap<String,StarbucksOrder> getAllOrders() {
        HashMap<String,StarbucksOrder> allOrders = new HashMap<>();

        Iterator<Map.Entry<String,StarbucksOrder>> it = this.activeOrders.entrySet().iterator(); // for all register ids...do this.
        while (it.hasNext()) {
            Map.Entry<String,StarbucksOrder> element = it.next();
            allOrders.put(element.getKey(), element.getValue());
        }

        return allOrders;
    }

    @DeleteMapping("/orders")
    public Status deleteAllOrders() {
        repo.deleteAll();

        Iterator<Map.Entry<String,StarbucksOrder>> it = this.activeOrders.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,StarbucksOrder> element = it.next();
            this.activeOrders.remove(element.getKey());
        }
        
        Status response = new Status();
        response.setStatus("All orders cleared!");

        return response;
    }
}