package com.onval.bakingapp.utils;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.provider.RecipeContract.IngredientsEntry;

import java.util.ArrayList;

/**
 * Created by gval on 06/10/2017.
 */

public class FormatUtils {
    @NonNull
    public static String formatStepInstructions(String instructions) {
        instructions = instructions.replaceFirst("\\.", ")"); //deal with first number

        String[] separatedInstructions = instructions.split("\\.");
        StringBuilder builder = new StringBuilder();

        for (String str : separatedInstructions) {
            builder.append(str).append(".\n");
        }

        return builder.toString();
    }

    public static String formatIngredients(Cursor i) {
        StringBuilder ingredientString = new StringBuilder();

        //Build the ingredient list
        while(i.moveToNext()) {
            ingredientString
                    .append("\u2022 ") //tab followed by a bullet point
                    .append(i.getString(IngredientsEntry.INGREDIENT)).append(" (")
                    .append(i.getString(IngredientsEntry.QUANTITY)).append(" ")
                    .append(i.getString(IngredientsEntry.MEASURE)).append(")\n");
        }

        //remove final trailing '\n'
        ingredientString.delete(ingredientString.length()-1, ingredientString.length());

        return ingredientString.toString();
    }

    public static String formatIngredientsForWidget(ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientString = new StringBuilder();

        //Build the ingredient list
        for (Ingredient i : ingredients) {
            ingredientString
                    .append("\u2022 ") //tab followed by a bullet point
                    .append(i.getName())
                    .append("\n");
        }

        //remove final trailing '\n'
        ingredientString.delete(ingredientString.length()-1, ingredientString.length());

        return ingredientString.toString();
    }
}
