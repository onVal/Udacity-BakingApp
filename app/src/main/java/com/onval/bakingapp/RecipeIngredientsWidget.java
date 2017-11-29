package com.onval.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Pair;
import android.widget.RemoteViews;

import com.onval.bakingapp.model.Fetcher;
import com.onval.bakingapp.presenter.IWidgetView;
import com.onval.bakingapp.presenter.WidgetRecipePresenter;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider implements IWidgetView {
    private WidgetRecipePresenter presenter;
    private Fetcher fetcher;

    static private Pair<String, String> ingredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String name, String ingredients) {
        //Create remote views
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);

        views.setTextViewText(R.id.widget_recipe_title, name);
        views.setTextViewText(R.id.widget_recipe_ingredients, ingredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        fetcher = new Fetcher(context);
        WidgetRecipePresenter presenter = new WidgetRecipePresenter(context, this, fetcher);
        presenter.loadIngredients();
    }

    @Override
    public void loadRecipeIngredient(Context context, AppWidgetManager appWidgetManager,
                                     int[] appWidgetIds, String name, String ingredients) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, name, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

