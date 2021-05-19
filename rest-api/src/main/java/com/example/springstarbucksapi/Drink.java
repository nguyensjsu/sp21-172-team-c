package com.example.springstarbucksapi;

public enum Drink {
    CAFFE_LATTE("Caffe Latte", 2.50),
    CAFFE_AMERICANO("Caffe Americano", 2.0),
    CAFFE_MOCHA("Caffe Mocha", 2.95),
    ESPRESSO("Espresso", 1.75),
    CAPPUCCINO("Cappuccino", 2.50);

    public final String label; // made public for cleaner semantics when accessing
    public final double basePrice;

    private Drink(String label, double basePrice) {
        this.label = label;
        this.basePrice = basePrice;
    }
}