package com.onval.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gval on 24/09/2017.
 */

public class Recipe implements Parcelable {
    public static final String RECIPE_PARCEL = "recipe-parcel";

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servingsNum;
    private String imagePath;

    @SuppressWarnings("unchecked")
    private Recipe(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.ingredients = parcel.readArrayList(Ingredient.class.getClassLoader());
        this.steps = parcel.readArrayList(Step.class.getClassLoader());
        this.servingsNum = parcel.readInt();
        this.imagePath = parcel.readString();
    }

    private Recipe(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
        this.steps = builder.steps;
        this.servingsNum = builder.servingsNum;
        this.imagePath = builder.imagePath;
    }

    public final static Parcelable.Creator<Recipe> CREATOR =
            new Parcelable.Creator<Recipe>() {
                @Override
                public Recipe[] newArray(int i) {
                    return new Recipe[i];
                }

                @Override
                public Recipe createFromParcel(Parcel parcel) {
                    return new Recipe(parcel);
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servingsNum);
        parcel.writeString(imagePath);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
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
        private ArrayList<Ingredient> ingredients; //optional
        private ArrayList<Step> steps; //optional
        private int servingsNum; //optional
        private String imagePath; //optional


        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder ingredients(ArrayList<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder steps(ArrayList<Step> steps) {
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
