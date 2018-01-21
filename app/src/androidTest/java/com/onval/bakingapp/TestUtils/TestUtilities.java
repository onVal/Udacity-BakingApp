package com.onval.bakingapp.TestUtils;

import android.support.test.espresso.idling.CountingIdlingResource;

import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.presenter.RecipePresenter;

import java.util.ArrayList;

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

    public static Recipe mockRecipe() {
        ArrayList<Step> mockSteps = new ArrayList<>();
        ArrayList<Ingredient> mockIngredients = new ArrayList<>();

        mockIngredients.add(new Ingredient("Yog", "oz", 20));
        mockIngredients.add(new Ingredient("Oat", "oz", 25));
        mockIngredients.add(new Ingredient("Milk", "lt", 1));


        mockSteps.add(new Step(0, "Zero", "desc0", "", ""));
        mockSteps.add(new Step(1, "One", "desc1", "", ""));
        mockSteps.add(new Step(2, "Two", "desc2", "", ""));
        mockSteps.add(new Step(3, "Three", "desc3", "", ""));


        return new Recipe.Builder(0, "Pancake")
                .steps(mockSteps)
                .ingredients(mockIngredients)
                .build();
    }

}
