package com.onval.bakingapp.adapter;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import static com.onval.bakingapp.RecipeIngredientsWidget.WIDGET_INGREDIENT;

/**
 * Created by gval on 30/11/2017.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("uffa", "onGetViewFactory() called!");

        String[] ingredients
                = intent.getStringArrayExtra(WIDGET_INGREDIENT);

//        ArrayList<Ingredient> mockIngredients = new ArrayList<>();
//        mockIngredients.add(new Ingredient("Yog", "oz", 20));
//        mockIngredients.add(new Ingredient("Egg", "oz", 5));
//        mockIngredients.add(new Ingredient("Flan", "oz", 3));
//        mockIngredients.add(new Ingredient("Meat", "oz", 2));


        return new WidgetRemoteViewsFactory(this.getApplicationContext(),
                ingredients);
    }
}
