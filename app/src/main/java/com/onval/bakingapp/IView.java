package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 26/09/2017.
 */

public interface IView {
    void onNoInternetConnection();
    void onAddRecipes(Set<Recipe> recipes);
    void displayNoRecipe();
}
