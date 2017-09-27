package com.onval.bakingapp.view;

import com.onval.bakingapp.Recipe;

import java.util.List;

/**
 * Created by gval on 26/09/2017.
 */

public interface IView {
    void onNoInternetConnection();
    void onAddRecipes(List<Recipe> recipes);
    void displayErrorMsg(String msg);
}
