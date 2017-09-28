package com.onval.bakingapp;

/**
 * Created by gval on 29/09/2017.
 */

public class Ingredient {
    private final String name;
    private final String measure;
    private final int quantity;

    public Ingredient(String name, String measure, int quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public int getQuantity() {
        return quantity;
    }
}
