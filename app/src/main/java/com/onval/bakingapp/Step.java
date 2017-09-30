package com.onval.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gval on 29/09/2017.
 */

public class Step implements Parcelable {
    public static final Parcelable.Creator<Step> CREATOR =
            new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[0];
        }
    };

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    @Override
    public int describeContents() {
        return 0;
    }
}
