package com.onval.bakingapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.Ingredient;
import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;
import com.onval.bakingapp.StepAdapter;

import java.util.ArrayList;

import static com.onval.bakingapp.Recipe.RECIPE_PARCEL;


public class StepDetailFragment extends Fragment implements View.OnClickListener {
    TextView ingredientsTV;
    int servings;

    RecyclerView stepsView;
    StepAdapter adapter;
    LinearLayoutManager layoutManager;

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

        stepsView = (RecyclerView) root.findViewById(R.id.steps_recyclerview);
        adapter = new StepAdapter(getContext(), recipeParcel.getSteps(), this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepsView.setLayoutManager(layoutManager);
        stepsView.setAdapter(adapter);


        return root;
    }

    private String formatIngredients(ArrayList<Ingredient> ingredients) {
        StringBuilder ingredientString = new StringBuilder();

        //TODO: use SpannableStringBuilder to improve formatting
        //todo: is there a better way to display 'servings' without having it as a field member?
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        //todo: make this right
        startActivity(intent);
    }
}
