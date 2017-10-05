package com.onval.bakingapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.adapter.StepAdapter;
import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;

import java.util.ArrayList;

import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;


public class StepDetailFragment extends Fragment implements IStepDetailView.Listener {
    public static final String STEP_INSTRUCTION_TAG = "step-instruction";

    public TextView ingredientsTV;

    private RecyclerView stepsView;
    private StepAdapter adapter;
    private LinearLayoutManager layoutManager;

    Recipe recipeParcel;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("StepDetailFragment", "onCreateView inside StepDetailFragment has been called!");
        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ingredientsTV = (TextView) root.findViewById(R.id.steps_ingredients);
        stepsView = (RecyclerView) root.findViewById(R.id.steps_recyclerview);

        //get the recipe info for this particular fragment
        recipeParcel = getActivity().getIntent().getExtras().getParcelable(RECIPE_PARCEL);

        //Show ingredients
        ingredientsTV.setText(formatIngredients(recipeParcel.getIngredients()));

        //set layoutManager for step list
        layoutManager = new LinearLayoutManager(getContext());
        stepsView.setLayoutManager(layoutManager);

        //set adapter for step list
        adapter = new StepAdapter(getContext(), recipeParcel.getSteps(), this);
        stepsView.setAdapter(adapter);

        return root;
    }

    private SpannableStringBuilder formatIngredients(ArrayList<Ingredient> ingredients) {
        SpannableStringBuilder ingredientString = new SpannableStringBuilder();

        String servings = String.valueOf(recipeParcel.getServingsNum());

        //Title: INGREDIENTS (for n people)
        ingredientString.append("INGREDIENTS (for ")
                .append(servings)
                .append(" people)\n")
                .setSpan( //todo: it should be working :-(
                new StyleSpan(android.graphics.Typeface.BOLD), 0, ingredientString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //Build the ingredient list
        for (Ingredient i : ingredients) {
            ingredientString
                    .append("\t\u2022 ") //tab followed by a bullet point
                    .append(i.getName()).append(" (")
                    .append(i.getQuantity() + "").append(" ")
                    .append(i.getMeasure()).append(")\n");
        }

        //remove final trailing '\n'
        ingredientString.delete(ingredientString.length()-1, ingredientString.length());

        return ingredientString;
    }

    @Override
    public void onStepClicked(int recipeId) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(STEP_INSTRUCTION_TAG, adapter.findStepById(recipeId));
        startActivity(intent);
    }
}
