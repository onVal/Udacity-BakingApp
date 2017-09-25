package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final View view;
    private final IFetcher model;

    public RecipePresenter(View view, IFetcher model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadRecipes() {
        //todo: should it check for internet connection with isOnline method here?
        Set<Recipe> recipeSet = model.fetchRecipes(); //todo: will this return null? beware!

        if (recipeSet.size() > 0)
            view.onAddRecipes(recipeSet);
        else
            view.displayNoRecipe();
    }
    
    // interface for View
    public interface View {
        void onNoInternetConnection();
        void onAddRecipes(Set<Recipe> recipes);
        void displayNoRecipe();
    }
}
