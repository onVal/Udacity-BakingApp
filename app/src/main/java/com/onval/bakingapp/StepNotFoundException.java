package com.onval.bakingapp;

/**
 * Created by gval on 10/10/2017.
 */

public class StepNotFoundException extends Exception {
    public StepNotFoundException() {
        super("Couldn't find element in array through id.");
    }
}
