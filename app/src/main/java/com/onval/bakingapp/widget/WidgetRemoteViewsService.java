package com.onval.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.onval.bakingapp.R;
import com.onval.bakingapp.provider.RecipeContract.IngredientsEntry;
import com.onval.bakingapp.provider.RecipeProvider;

import static com.onval.bakingapp.widget.RecipeIngredientsWidget.DISPLAYED_RECIPE_ID;

/**
 * Created by gval on 30/11/2017.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
//        String[] ingredients
//                = intent.getStringArrayExtra(WIDGET_INGREDIENT);

        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }

    class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
//        private String[] ingredients;
        private Cursor ingredients;

        public WidgetRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.widget_shared_prefs), MODE_PRIVATE);
            int recipeId = pref.getInt(DISPLAYED_RECIPE_ID, 0);

            ingredients = context.getContentResolver().query(
                    RecipeProvider.getIngredUri(recipeId),
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public int getCount() {
            if (ingredients == null)
                return 0;

            return ingredients.getCount();
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
            if (ingredients == null || ingredients.getCount() == 0)
                return null;

            ingredients.moveToPosition(i);

            RemoteViews views =
                    new RemoteViews(context.getPackageName(), R.layout.single_widget_ingredient);
            views.setTextViewText(R.id.single_widget_ingredient, ingredients.getString(IngredientsEntry.INGREDIENT));

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
