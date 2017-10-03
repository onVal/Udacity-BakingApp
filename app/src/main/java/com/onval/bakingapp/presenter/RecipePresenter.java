package com.onval.bakingapp.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.IFetcher;
import com.onval.bakingapp.view.IRecipeView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final IRecipeView view;
    private final IFetcher model;

    public RecipePresenter(IRecipeView view, IFetcher model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadRecipes() {
        //todo: should it check for internet connection with isOnline method here?

        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Recipe> recipes = (ArrayList<Recipe>) model.parseRecipes(response);

                if (recipes.size() > 0)
                    view.addRecipes(recipes);

                else
                    view.displayErrorMsg("No recipes returned.");
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.displayErrorMsg("Couldn't load recipes");
            }
        };

        model.fetchFromServer(response, error);
    }
}
