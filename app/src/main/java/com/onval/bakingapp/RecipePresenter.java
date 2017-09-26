package com.onval.bakingapp;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.HashSet;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final IView IView;
    private final IFetcher model;

    public RecipePresenter(IView IView, IFetcher model) {
        this.IView = IView;
        this.model = model;
    }

    @Override
    public void loadRecipes() {
        //todo: should it check for internet connection with isOnline method here?

        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                HashSet<Recipe> recipes = model.parseRecipes(response);

                if (recipes.size() > 0)
                    IView.onAddRecipes(recipes);

                else
                    IView.displayErrorMsg("No recipes returned.");
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                IView.displayErrorMsg("Couldn't load recipes");
                Log.e("Response.ErrorListener", error.toString());
            }
        };

        model.fetchFromServer(response, error);
    }
}
