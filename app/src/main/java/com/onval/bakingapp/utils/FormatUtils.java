package com.onval.bakingapp.utils;

import android.support.annotation.NonNull;

import com.onval.bakingapp.data.Ingredient;

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

    public static String formatIngredients(ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientString = new StringBuilder();

//        String servings = String.valueOf(recipeParcel.getServingsNum());

//        //Title: INGREDIENTS (for n people)
//        ingredientString.append("INGREDIENTS (for ")
//                .append(servings)
//                .append(" people)\n")
//                .setSpan(
//                new StyleSpan(android.graphics.Typeface.BOLD), 0, ingredientString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //Build the ingredient list
        for (Ingredient i : ingredients) {
            ingredientString
                    .append("\u2022 ") //tab followed by a bullet point
                    .append(i.getName()).append(" (")
                    .append(i.getQuantity() + "").append(" ")
                    .append(i.getMeasure()).append(")\n");
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
