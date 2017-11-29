package com.onval.bakingapp.presenter;

import android.support.test.espresso.idling.CountingIdlingResource;

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

    public static final String IDLING_RESOURCE_TAG = "load_recipes";

    static public CountingIdlingResource idlingResource =
            new CountingIdlingResource(IDLING_RESOURCE_TAG);

    public RecipePresenter(IRecipeView view, IFetcher model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadRecipes() {
        //todo: should it check for internet connection with isOnline method here?

        //Preparing callback methods
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (idlingResource != null)
                    idlingResource.decrement();

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
                if (idlingResource != null)
                    idlingResource.decrement();
                view.displayErrorMsg("Couldn't load recipes");
            }
        };

        //THIS IS WHERE THIS METHOD STARTS!
        if (idlingResource != null)
            idlingResource.increment(); //this is for testing purposes
        model.fetchFromServer(response, error);
    }
}
