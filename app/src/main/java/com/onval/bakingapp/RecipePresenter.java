package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final View view;
    private final IFetcher model;

    RecipePresenter(View view) {
        this.view = view;
        model = new Fetcher(this);
    }

    @Override
    public void loadRecipes() {
        Set<Recipe> recipeSet = model.fetchRecipes();
        view.onAddRecipes(recipeSet);
    }

    // interface for View
    public interface View {
        void onAddRecipes(Set<Recipe> recipes);
    }
}
