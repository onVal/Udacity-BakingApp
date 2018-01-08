package com.onval.bakingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by gval on 08/01/2018.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
