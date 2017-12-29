package com.onval.bakingapp.adapter;

import android.content.Intent;
import android.widget.RemoteViewsService;

import static com.onval.bakingapp.RecipeIngredientsWidget.WIDGET_INGREDIENT;

/**
 * Created by gval on 30/11/2017.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String[] ingredients
                = intent.getStringArrayExtra(WIDGET_INGREDIENT);

        return new WidgetRemoteViewsFactory(this.getApplicationContext(),
                ingredients);
    }
}
