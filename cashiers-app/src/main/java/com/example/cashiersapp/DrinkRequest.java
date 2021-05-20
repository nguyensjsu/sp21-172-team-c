package com.example.cashiersapp;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DrinkRequest {
    public String drink;
    public String milk;
    public String size;

    public DrinkRequest(String drink, String milk, String size) {
        this.drink = drink;
        this.milk = milk;
        this.size = size;
    }

    public static String toJson(String drink, String milk, String size) {
        ObjectMapper mapper = new ObjectMapper();
        DrinkRequest request = new DrinkRequest(drink, milk, size);

        String json = "";
        try {
            json = mapper.writeValueAsString(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}