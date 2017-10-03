package com.onval.bakingapp.view;

import com.onval.bakingapp.data.Recipe;

import java.util.List;

/**
 * Created by gval on 26/09/2017.
 */

public interface IRecipeView {
    void addRecipes(List<Recipe> recipes);
    void displayErrorMsg(String msg);
    void onNoInternetConnection();

    interface Listener {
        void onRecipeClicked(int recipeId);
    }
}
