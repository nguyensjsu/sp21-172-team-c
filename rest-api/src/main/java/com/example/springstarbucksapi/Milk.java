package com.example.springstarbucksapi;

public enum Milk {
    WHOLE_MILK("Whole Milk"),
    TWO_PERCENT_MILK("2% Milk"),
    NONFAT_MILK("Nonfat Milk"),
    ALMOND_MILK("Almond Milk"),
    SOY_MILK("Soy Milk");

    public final String label;

    private Milk(String label) {
        this.label = label;
    }
}