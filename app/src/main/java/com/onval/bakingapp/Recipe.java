package com.onval.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gval on 24/09/2017.
 */

public class Recipe implements Parcelable {
    public static final String RECIPE_PARCEL = "recipe-parcel";

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servingsNum;
    private String imagePath;

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
//                    id = parcel.readInt();
//                    name = parcel.readString();
//                    parcel.readList(ingredients, null);
//                    parcel.readList(steps, null);
//                    servingsNum = parcel.readInt();
//                    imagePath = parcel.readString();

                    return new Builder(parcel.readInt(), parcel.readString())
//                            .ingredients(ingredients)
//                            .steps(steps)
                            .servings(parcel.readInt())
                            .image(parcel.readString())
                            .build();
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
