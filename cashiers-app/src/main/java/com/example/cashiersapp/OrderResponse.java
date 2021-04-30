package com.example.cashiersapp;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderResponse {
    public String id;
    public String drink;
    public String milk;
    public String size;
    public String total;
    public String status;

    public OrderResponse() {}

    public static OrderResponse fromJson(String json) {
        OrderResponse response = new OrderResponse();
        ObjectMapper mapper = new ObjectMapper();

        try {
            response = mapper.readValue(json, OrderResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}