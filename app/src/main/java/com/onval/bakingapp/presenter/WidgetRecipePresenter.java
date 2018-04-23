package com.onval.bakingapp.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.R;
import com.onval.bakingapp.widget.RecipeIngredientsWidget;
import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.IFetcher;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.onval.bakingapp.widget.RecipeIngredientsWidget.DISPLAYED_RECIPE_ID;

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
        model.fetchFromServer(response, error);
    }

    private Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<Recipe> recipes = (ArrayList<Recipe>) model.parseRecipes(response);


            Log.d("WidgetPresenter", "onResponse: a call to the server is being made");
            if (recipes.size() > 0) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetId = appWidgetManager.getAppWidgetIds(
                        new ComponentName(context, RecipeIngredientsWidget.class));

                SharedPreferences pref = context.getSharedPreferences
                        (context.getString(R.string.widget_shared_prefs), 0);

                if (!pref.contains(DISPLAYED_RECIPE_ID)) {
                    pref.edit().putInt(DISPLAYED_RECIPE_ID, 1).apply();
                }

                int index = pref.getInt(DISPLAYED_RECIPE_ID, 0);
                Recipe currentRecipe = recipes.get(index);

                Log.d("WidgetPresenter", "recipe index is " + index);

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_ingredient_list);

                view.loadRecipeIngredient(context, appWidgetManager, appWidgetId,
                        currentRecipe.getName());
            }

//                else
//                    view.displayErrorMsg("No recipes returned.");
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//                view.displayErrorMsg("Couldn't load recipes");
        }
    };

    private String[] extractIngredientNames(ArrayList<Ingredient> ingr) {
        String[] names = new String[ingr.size()];

        for (int i = 0; i < ingr.size(); i++) {
            names[i] = ingr.get(i).getName();
        }

        return names;
    }
}
