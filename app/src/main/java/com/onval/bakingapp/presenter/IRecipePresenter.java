package com.onval.bakingapp.presenter;

/**
 * Created by gval on 24/09/2017.
 */

public interface IRecipePresenter {
    void loadRecipes();

    interface Callback {
        void onNoConnection();
    }
}
