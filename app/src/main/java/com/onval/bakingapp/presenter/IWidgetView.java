package com.onval.bakingapp.presenter;

import android.appwidget.AppWidgetManager;
import android.content.Context;

/**
 * Created by gval on 14/11/2017.
 */

public interface IWidgetView {
    void loadRecipeIngredient(Context context, AppWidgetManager appWidgetManager,
                              int[] appWidgetIds, String name);
}