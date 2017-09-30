package com.onval.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gval on 29/09/2017.
 */

public class Ingredient implements Parcelable {
    public static final Parcelable.Creator<Ingredient> CREATOR =
            new Parcelable.Creator<Ingredient>() {
                @Override
                public Ingredient createFromParcel(Parcel parcel) {
                    return new Ingredient(
                            parcel.readString(),
                            parcel.readString(),
                            parcel.readInt()
                    );
                }

                @Override
                public Ingredient[] newArray(int i) {
                    return new Ingredient[i];
                }
            };

    private String name;
    private String measure;
    private int quantity;

    public Ingredient(String name, String measure, int quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(measure);
        parcel.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
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
