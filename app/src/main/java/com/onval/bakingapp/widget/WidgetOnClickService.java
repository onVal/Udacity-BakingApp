package com.onval.bakingapp.widget;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.onval.bakingapp.R;

/**
 * Created by gval on 29/12/2017.
 */

public class WidgetOnClickService extends IntentService {
    public WidgetOnClickService() {
        super(WidgetOnClickService.class.getSimpleName());
    }

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.widget_shared_prefs), 0);
        int currentValue = preferences.getInt(RecipeIngredientsWidget.DISPLAYED_RECIPE_ID, 0);

        Log.d("debug", "previous value of pref: " + preferences.getInt(RecipeIngredientsWidget.DISPLAYED_RECIPE_ID, 0));

        String action = intent.getAction();

        Log.d("debug", "actions is: " + action);

        //update shared preferences
        if (action.equals(getString(R.string.actionClickPreviousWidget))) {
            Log.d("debug", "click left");
            if (currentValue > 0) {
                preferences.edit().putInt(RecipeIngredientsWidget.DISPLAYED_RECIPE_ID, currentValue - 1).commit();
            }
        }
        else if (action.equals(getString(R.string.actionClickNextWidget))) {
            Log.d("debug", "click right");
            preferences.edit().putInt(RecipeIngredientsWidget.DISPLAYED_RECIPE_ID, currentValue+1).commit();

        }

        Log.d("debug", "now pref is: " + preferences.getInt(RecipeIngredientsWidget.DISPLAYED_RECIPE_ID, 0));

        //call widget update
        Intent updateIntent = new Intent(getApplication(), RecipeIngredientsWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), RecipeIngredientsWidget.class));
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(updateIntent);
    }
}
