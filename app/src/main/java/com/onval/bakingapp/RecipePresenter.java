package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final View view;
    private final IFetcher model;

    public RecipePresenter(View view) {
        this.view = view;
        model = new Fetcher(this);
    }

    @Override
    public void loadRecipes() {
        if (NetworkUtilities.isOnline()) {
            //todo: will this return null? beware!
            Set<Recipe> recipeSet = model.fetchRecipes();
            view.onAddRecipes(recipeSet);
        } else {
            view.onNoInternetConnection();
        }
    }
    
    // interface for View
    public interface View {
        void onNoInternetConnection();
        void onAddRecipes(Set<Recipe> recipes);
    }
}
