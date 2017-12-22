package com.onval.bakingapp;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.onval.bakingapp.adapter.WidgetRemoteViewsService;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.Fetcher;
import com.onval.bakingapp.presenter.IWidgetView;
import com.onval.bakingapp.presenter.WidgetRecipePresenter;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider implements IWidgetView {

    public static final String WIDGET_INGREDIENT = "widget-ingredient";

    ArrayList<Recipe> widgetRecipes;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String name, String[] ingredients) {
        //Create remote views
        RemoteViews views = new RemoteViews(context.getPackageName(),
                                            R.layout.recipe_ingredients_widget);

        views.setTextViewText(R.id.widget_recipe_title, name);

        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(WIDGET_INGREDIENT, ingredients);
        views.setRemoteAdapter(R.id.widget_recipe_ingredient_list, intent);
        //todo: handle empty view with views.setEmptyView()

        //todo: handle onclick events with pendingintents
//        PendingIntent pendingIntent =
//                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.widget_recipe_ingredients, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetRecipePresenter presenter =
                new WidgetRecipePresenter(context, this, new Fetcher(context));
        presenter.loadIngredients();
    }

    @Override
    public void storeRecipes(ArrayList<Recipe> recipes) {
        widgetRecipes = recipes;
    }

    @Override
    public void loadRecipeIngredient(Context context, AppWidgetManager appWidgetManager,
                                     int[] appWidgetIds, String name,
                                     String[] ingredients) {
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

