package com.onval.bakingapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;


<<<<<<< HEAD
public class StepDetailFragment extends Fragment {
    public static final String STEP_LIST_TAG = "step-instruction";
    public static final String STEP_POSITION_TAG = "step-position-tag";
=======
public class StepDetailFragment extends Fragment implements IStepDetailView.Listener {
    public static final String STEP_INSTRUCTION_TAG = "step-instruction";
    public static final String STEP_ID_TAG = "step-position-tag";
>>>>>>> 5f7ac598b913e4fc7afbc3abd375a0d2f6e56a70

    @BindView(R.id.steps_ingredients) TextView ingredientsTV;
    @BindView(R.id.steps_recyclerview) RecyclerView stepsView;

<<<<<<< HEAD
=======
    private StepAdapter adapter;
>>>>>>> 5f7ac598b913e4fc7afbc3abd375a0d2f6e56a70
    private LinearLayoutManager layoutManager;
    private

    Recipe recipeParcel;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, root);

        //get the recipe info for this particular fragment
        recipeParcel = getActivity().getIntent().getExtras().getParcelable(RECIPE_PARCEL);

        //Show ingredients
        ingredientsTV.setText(formatIngredients(recipeParcel.getIngredients()));

        //set layoutManager for step list
        layoutManager = new LinearLayoutManager(getContext());
        stepsView.setLayoutManager(layoutManager);

        //set parent activity's adapter
        stepsView.setAdapter(((StepDetailActivity)getActivity()).getStepAdapter());

        //set divider for recycler view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        stepsView.addItemDecoration(dividerItemDecoration);

        return root;
    }

    private SpannableStringBuilder formatIngredients(ArrayList<Ingredient> ingredients) {
        SpannableStringBuilder ingredientString = new SpannableStringBuilder();

        String servings = String.valueOf(recipeParcel.getServingsNum());

        //Title: INGREDIENTS (for n people)
        ingredientString.append("INGREDIENTS (for ")
                .append(servings)
                .append(" people)\n")
                .setSpan(
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

<<<<<<< HEAD
    public interface OnStepClickListener {
            void onStepClicked(int stepId);
=======
    @Override
    public void onStepClicked(int stepId) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(STEP_INSTRUCTION_TAG, adapter.getStepList());
        intent.putExtra(STEP_ID_TAG, stepId);
        startActivity(intent);
>>>>>>> 5f7ac598b913e4fc7afbc3abd375a0d2f6e56a70
    }
}
