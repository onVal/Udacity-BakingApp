package com.onval.bakingapp.presenter;

import android.appwidget.AppWidgetManager;
import android.content.Context;

import com.onval.bakingapp.data.Recipe;

import java.util.ArrayList;

/**
 * Created by gval on 14/11/2017.
 */

public interface IWidgetView {
    void storeRecipes(ArrayList<Recipe> recipes);
    void loadRecipeIngredient(Context context, AppWidgetManager appWidgetManager,
                              int[] appWidgetIds, String name, String[] ingredients);
}