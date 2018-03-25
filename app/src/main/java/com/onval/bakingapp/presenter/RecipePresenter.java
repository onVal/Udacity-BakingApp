package com.onval.bakingapp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.IFetcher;
import com.onval.bakingapp.provider.RecipeContract.RecipesEntry;
import com.onval.bakingapp.provider.RecipeProvider;
import com.onval.bakingapp.view.IRecipeView;

import org.json.JSONArray;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Created by gval on 24/09/2017.
 */

public class RecipePresenter implements IRecipePresenter {
    private final IRecipeView view;
    private final IFetcher model;
    private final Context context;

    public static final String IDLING_RESOURCE_TAG = "load_recipes";

    @Nullable
    private CountingIdlingResource idlingResource;

    public RecipePresenter(Context c, IRecipeView view, IFetcher model,
                           @Nullable CountingIdlingResource idlingResource) {
        this.view = view;
        this.model = model;
        this.context = c;
        this.idlingResource = idlingResource;
    }

    @Override
    public void loadRecipes() {
        //todo: should it check for internet connection with isOnline method here?
        if (idlingResource != null)
            idlingResource.increment(); //this is for testing purposes
        model.fetchFromServer(response, error);

    }

    //Preparing callback methods
    private Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            // see if there are any recipes present
            Cursor c = context.getContentResolver().
                    query(RecipesEntry.RECIPE_URI,
                            null,
                            null,
                            null,
                            null);

            // put stuff in provider
            ArrayList<Recipe> recipes = (ArrayList<Recipe>) model.parseRecipes(response);

            if (recipes.size() > 0) {
                //if there are already recipes, delete them (and everything else)
                if (c.moveToFirst()) {
                    context.getContentResolver()
                            .delete(RecipesEntry.RECIPE_URI, null, null);
                }

                //insert recipes
                for (Recipe r : recipes) {
                    RecipeProvider.insertRecipe(context.getContentResolver(), r);
                }

                view.displayRecipes();
            }

            else {
                view.displayErrorMsg("No recipes returned.");
            }

            if (idlingResource != null)
                idlingResource.decrement();
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (idlingResource != null)
                idlingResource.decrement();
            view.displayErrorMsg("Couldn't load recipes");
        }
    };
}
