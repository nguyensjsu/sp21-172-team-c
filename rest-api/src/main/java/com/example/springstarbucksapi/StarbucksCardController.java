package com.example.springstarbucksapi;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@RestController
public class StarbucksCardController {
    private final StarbucksCardRepository repo;

    public StarbucksCardController(StarbucksCardRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/cards")
    List<StarbucksCard> getCards() {
        List<StarbucksCard> allCards = repo.findAll();
        allCards.forEach(card -> card.setStatus(""));

        return repo.saveAll(allCards);
    }

    @PostMapping("/cards")
    StarbucksCard createCard() {
        StarbucksCard newCard = new StarbucksCard();

        Random generator = new Random();
        int num = generator.nextInt(900000000) + 100000000;
        int code  = generator.nextInt(900) + 100;

        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(20.00);
        newCard.setActivated(false);
        newCard.setStatus("New Card");

        return repo.save(newCard);
    }

    @GetMapping("/cards/{num}")
    StarbucksCard getCardByNum(@PathVariable String num) {
        StarbucksCard card = repo.findByCardNumber(num);

        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card not found!");
        } else {
            card.setStatus("");
            return repo.save(card);
        }
    }

    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(
        @PathVariable String num, 
        @PathVariable String code
    ){
        StarbucksCard card = repo.findByCardNumber(num);

        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card not found!");
        }
        if (card.getCardCode().equals(code)) {
            card.setActivated(true);
            card.setStatus("");
            repo.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card not valid!");
        }

        return card;
    }

    @DeleteMapping("/cards")
    Status delete() {
        repo.deleteAll();

        Status response = new Status();
        response.setStatus("All cards cleared!");

        return response;
    }
}