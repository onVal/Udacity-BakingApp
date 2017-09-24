package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final View view;

    RecipePresenter(View view) {
        this.view = view;
    }

    @Override
    public void fetchRecipes() {
    }

    // interface for View
    public interface View {
        void onAddRecipes(Set<Recipe> recipes);
    }
}
