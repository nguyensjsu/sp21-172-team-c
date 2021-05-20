package com.example.cashiersapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${spring-starbucks.api.endpoint}")
    private String apiEndpoint;
    @Value("${spring-starbucks.api.key}")
    private String apiKey;

    @GetMapping
    public String cashiers(Model model) {
        model.addAttribute("storeID", "5012349"); // will default store to Dub-C
        return "cashiers";
    }

    @PostMapping
    public String post(
        @RequestParam(name="action") String action,
        @RequestParam(name="store") String storeID, // equivalent to "regid" in the rest api
        @RequestParam(name="drink") String drink,
        @RequestParam(name="milk") String milk,
        @RequestParam(name="size") String size,
        @RequestParam(name="cardNum") String cardNum,
        Model model
        ){
        HttpClient client = HttpClient.newHttpClient();

        // --- ACTIONS ---
        if (action.equals("Add to Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint + "/order/register/" + storeID))
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(DrinkRequest.toJson(drink, milk, size)))
                .build();

            try {
                HttpResponse httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                System.out.println(httpResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("doScrollToRegister", true);
            getOrder(storeID, client, model);
        } else if (action.equals("Get Order")) {
            model.addAttribute("doScrollToRegister", true);
            getOrder(storeID, client, model);
        } else if (action.equals("Place Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint + "/order/register/" + storeID + "/pay/" + cardNum)) // create/activate card before testing this, and put cc num at end
                .header("apikey", apiKey)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

            try {
                HttpResponse httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                System.out.println(httpResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("didPlaceOrder", true);
        } else if (action.equals("Delete Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint + "/order/register/" + storeID))
                .header("apikey", apiKey)
                .DELETE()
                .build();
            
            try {
                HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                System.out.println(httpResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("didDeleteOrder", true);
        }

        // for the script to set/persist values selected
        model.addAttribute("storeID", storeID);
        model.addAttribute("drink", drink);
        model.addAttribute("milk", milk);
        model.addAttribute("size", size);

        return "cashiers";
    }

    public void getOrder(String storeID, HttpClient client, Model model) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint + "/order/register/" + storeID))
                .header("apikey", apiKey)
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
            OrderResponse orderResponse = OrderResponse.fromJson(httpResponse.body());

            System.out.println(httpResponse.body());

            model.addAttribute("order", orderResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}