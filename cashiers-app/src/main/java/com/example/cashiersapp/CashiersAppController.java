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
    public String cashiers() {
        return "cashiers";
    }

    @PostMapping
    public String post(
        @RequestParam(name="action") String action, 
        Model model
        ){
        HttpClient client = HttpClient.newHttpClient();

        // --- GET ORDER TEST ---
        if (action.equals("Get Order")) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/order/register/5012349"))
                .GET()
                .build();

            try {
                HttpResponse<String> httpResponse = client.send(httpRequest, BodyHandlers.ofString());
                OrderResponse orderResponse = OrderResponse.fromJson(httpResponse.body());

                model.addAttribute("order", orderResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "cashiers";
    }
}