package com.onval.bakingapp;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

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

        Response.Listener<JSONObject> response = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                HashSet<Recipe> recipes = model.parseRecipes(response);

                if (recipes == null)
                    //todo: handle this

                if (recipes.size() > 0)
                    IView.onAddRecipes(recipes);
                else
                    IView.displayNoRecipe();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: handle error
            }
        };

        model.fetchRecipes(response, error); //todo: will this return null? beware!
    }
}
