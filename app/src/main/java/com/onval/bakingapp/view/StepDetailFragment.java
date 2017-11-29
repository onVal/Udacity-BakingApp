package com.onval.bakingapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;
import static com.onval.bakingapp.utils.FormatUtils.formatIngredients;


public class StepDetailFragment extends Fragment {
    public static final String STEP_LIST_TAG = "step-instruction";
    public static final String STEP_POSITION_TAG = "step-position-tag";

    @BindView(R.id.steps_ingredients_title)TextView titleTV;
    @BindView(R.id.steps_ingredients) TextView ingredientsTV;
    @BindView(R.id.steps_recyclerview) RecyclerView stepsView;

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
        titleTV.setText("INGREDIENTS (" + recipeParcel.getServingsNum() + " servings)");
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


    public interface OnStepClickListener {
            void onStepClicked(int stepId);
    }
}
