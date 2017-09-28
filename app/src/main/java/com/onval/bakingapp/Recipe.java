package com.onval.bakingapp;

/**
 * Created by gval on 24/09/2017.
 */

public class Recipe {
    private final String name;
    private final String imagePath;
    private final int servingsNum;

    private Recipe(Builder builder) {
        this.name = builder.name;
        this.imagePath = builder.imagePath;
        this.servingsNum = builder.servingsNum;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return imagePath;
    }

    public int getServings() {
        return servingsNum;
    }


    public static class Builder {
        private String name;
        private String imagePath;
        private int servingsNum;

        public Builder(String name) {
            this.name = name;
        }

        public Builder image(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder servings(int servingsNum) {
            this.servingsNum = servingsNum;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }
}
