package com.onval.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.onval.bakingapp.R;

import static com.onval.bakingapp.widget.RecipeIngredientsWidget.WIDGET_INGREDIENT;

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

    class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private String[] ingredients;

        public WidgetRemoteViewsFactory(Context context, String[] ingredients) {
            this.context = context;
            this.ingredients = ingredients;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public int getCount() {
            if (ingredients.length == 0) return 0;
            return ingredients.length;
        }

        @Override
        public void onDataSetChanged() {
            //todo: do I need to implent this?
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (ingredients == null || ingredients.length == 0)
                return null;

            RemoteViews views =
                    new RemoteViews(context.getPackageName(), R.layout.single_widget_ingredient);
            views.setTextViewText(R.id.single_widget_ingredient, ingredients[i]);

            return views;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
