package com.onval.bakingapp.TestUtils;

import com.onval.bakingapp.presenter.RecipePresenter;

import static android.support.test.espresso.Espresso.registerIdlingResources;

/**
 * Created by gval on 03/11/2017.
 */

public class TestUtilities {
    static final public int NUTELLAPIE_POSITION = 0;
    static final public int BROWNIES_POSITION = 1;
    static final public int YELLOWCAKE_POSITION = 2;
    static final public int CHEESECAKE_POSITION = 3;

    public static void registerCountingIdlingResource() {
        registerIdlingResources(RecipePresenter.idlingResource);
    }
}
