package com.onval.bakingapp.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.RecipeIngredientsWidget;
import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.IFetcher;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by gval on 14/11/2017.
 */

public class WidgetRecipePresenter {
    private Context context;
    private IWidgetView view;
    private IFetcher model;

    public WidgetRecipePresenter(Context context, IWidgetView view, IFetcher model) {
        this.view = view;
        this.model = model;
        this.context = context;
    }

    public void loadIngredients() {

        //todo: should it check for internet connection with isOnline method here?

        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Recipe> recipes = (ArrayList<Recipe>) model.parseRecipes(response);

                if (recipes.size() > 0) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientsWidget.class));

                    view.storeRecipes(recipes); // stores recipes for future updates

                    view.loadRecipeIngredient(context, appWidgetManager, appWidgetId,
                            recipes.get(0).getName(), //todo: change this to remember last recipe selected
                            extractIngredientNames(recipes.get(0).getIngredients()));
                }

//                else
//                    view.displayErrorMsg("No recipes returned.");
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                view.displayErrorMsg("Couldn't load recipes");
            }
        };

        model.fetchFromServer(response, error);
    }

    private String[] extractIngredientNames(ArrayList<Ingredient> ingr) {
        String[] names = new String[ingr.size()];

        for (int i = 0; i < ingr.size(); i++) {
            names[i] = ingr.get(i).getName();
        }

        return names;
    }
}
