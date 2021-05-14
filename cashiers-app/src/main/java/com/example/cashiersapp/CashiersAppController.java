package com.example.cashiersapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Controller
@RequestMapping("/")
public class CashiersAppController {

    @GetMapping
    public String cashiers(Model model) {
        model.addAttribute("storeID", "5012349"); // will default store to Dub-C
        return "cashiers";
    }

    @PostMapping
    public String post(
        @RequestParam(name="action") String action,
        @RequestParam(name="store") String storeID, // equivalent to "regid" in the rest api
        @RequestParam(name="drink") String drinkID,
        @RequestParam(name="milk") String milkID,
        @RequestParam(name="size") String sizeID,
        Model model
        ){
        HttpClient client = HttpClient.newHttpClient();

        // --- GET ORDER TEST ---
        if (action.equals("Get Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/order/register/" + storeID))
                .GET()
                .build();

            try {
                HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                OrderResponse orderResponse = OrderResponse.fromJson(httpResponse.body());

                model.addAttribute("order", orderResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("Place Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/order/register/" + storeID + "/pay/" + "297007900")) // create/activate card before testing this, and put cc num at end
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

            try {
                HttpResponse httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                System.out.println(httpResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("Delete Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/order/register/" + storeID))
                .DELETE()
                .build();
            
            try {
                HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                System.out.println(httpResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("storeID", storeID);

        return "cashiers";
    }
}