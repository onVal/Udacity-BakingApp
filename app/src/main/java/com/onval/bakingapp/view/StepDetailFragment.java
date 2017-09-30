package com.onval.bakingapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.Ingredient;
import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;

import java.util.ArrayList;

import static com.onval.bakingapp.Recipe.RECIPE_PARCEL;


public class StepDetailFragment extends Fragment {
    TextView ingredientsTV;
    int servings;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);
        Recipe recipeParcel = getActivity().getIntent().getExtras().getParcelable(RECIPE_PARCEL);


        servings = recipeParcel.getServingsNum();

        ingredientsTV = (TextView) root.findViewById(R.id.steps_ingredients);

        ArrayList<Ingredient> ingredients = recipeParcel.getIngredients();
        ingredientsTV.setText(formatIngredients(ingredients));

        return root;
    }

    private String formatIngredients(ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientString = new StringBuilder();

        //TODO: use SpannableStringBuilder to improve formatting
        ingredientString.append("INGREDIENTS (for ").append(servings).append(" people)\n");

        for (Ingredient i : ingredients) {
            ingredientString
                    .append("\u2022 ") //bullet point
                    .append(i.getName()).append(" (")
                    .append(i.getQuantity()).append(" ")
                    .append(i.getMeasure()).append(")\n");
        }

        return ingredientString.toString();
    }
}
