package com.onval.bakingapp.TestUtils;

import android.support.test.espresso.idling.CountingIdlingResource;

import com.onval.bakingapp.presenter.RecipePresenter;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static com.onval.bakingapp.presenter.RecipePresenter.IDLING_RESOURCE_TAG;

/**
 * Created by gval on 03/11/2017.
 */

public class TestUtilities {
    static final public int NUTELLAPIE_POSITION = 0;
    static final public int BROWNIES_POSITION = 1;
    static final public int YELLOWCAKE_POSITION = 2;
    static final public int CHEESECAKE_POSITION = 3;

    public static void setupCountingIdlingResource() {
        if (RecipePresenter.idlingResource == null)
            RecipePresenter.idlingResource = new CountingIdlingResource(IDLING_RESOURCE_TAG);
        registerIdlingResources(RecipePresenter.idlingResource);
    }

    public static void teardownCountingIdlingResource() {
        unregisterIdlingResources(RecipePresenter.idlingResource);
        RecipePresenter.idlingResource = null;
    }

}
