package com.onval.bakingapp;

import java.util.Set;

/**
 * Created by gval on 25/09/2017.
 */

public class Fetcher implements IFetcher {
    private IRecipePresenter callbacks;

    Fetcher(IRecipePresenter callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public Set<Recipe> fetchRecipes() {
        //TODO: to implement
        return null;
    }
}
