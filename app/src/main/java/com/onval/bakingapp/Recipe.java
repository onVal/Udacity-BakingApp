package com.onval.bakingapp;

import java.util.List;

/**
 * Created by gval on 24/09/2017.
 */

public class Recipe {
    private final int id;
    private final String name;
    private final List<Ingredient> ingredients;
    private final List<Step> steps;
    private final int servingsNum;
    private final String imagePath;

    private Recipe(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
        this.steps = builder.steps;
        this.servingsNum = builder.servingsNum;
        this.imagePath = builder.imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServingsNum() {
        return servingsNum;
    }

    public String getImagePath() {
        return imagePath;
    }


    public static class Builder {
        private int id; //required
        private String name; //required
        private List<Ingredient> ingredients; //optional
        private List<Step> steps; //optional
        private int servingsNum; //optional
        private String imagePath; //optional


        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder ingredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder steps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

        public Builder servings(int servingsNum) {
            this.servingsNum = servingsNum;
            return this;
        }

        public Builder image(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }
}
