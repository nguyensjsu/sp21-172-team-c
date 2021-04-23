package com.example.springstarbucksapi;

public enum Size {
    SHORT("Short", 1.0),
    TALL("Tall", 1.15),
    GRANDE("Grande", 1.30),
    VENTI("Venti", 1.45),
    YOUR_OWN_CUP("Your Own Cup", 1.45);

    public final String label;
    public final double multiplier;

    private Size(String label, double multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }
}