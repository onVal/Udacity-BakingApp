package com.onval.bakingapp.view;

/**
 * Created by gval on 26/09/2017.
 */

public interface IRecipeView {
    void displayRecipes();
    void displayErrorMsg(String msg);
    void onNoInternetConnection();

    interface Listener {
        void onRecipeClicked(int recipeId);
    }
}
