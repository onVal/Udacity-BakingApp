package com.onval.bakingapp.view;


import android.database.Cursor;
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
import com.onval.bakingapp.provider.RecipeContract.RecipesEntry;
import com.onval.bakingapp.provider.RecipeProvider;
import com.onval.bakingapp.utils.FormatUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {
    public static final String RECIPE_ID_TAG = "recipe-id-tag";
    public static final String STEP_POSITION_TAG = "step-position-tag";
    public static final String STEP_LIST_TAG = "step-list-tag";


    @BindView(R.id.steps_ingredients_title)TextView titleTV;
    @BindView(R.id.steps_ingredients) TextView ingredientsTV;
    @BindView(R.id.steps_recyclerview) RecyclerView stepsView;

    private LinearLayoutManager layoutManager;
    private Cursor c;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, root);

        //get the recipe info for this particular fragment
        int recipeId = getActivity().getIntent().getExtras().getInt(RECIPE_ID_TAG);

        c = getActivity().getContentResolver().query(RecipesEntry.RECIPE_URI, null,
                RecipesEntry._ID + " = ?", new String[]{String.valueOf(recipeId)}, null);

        Cursor i = getActivity().getContentResolver().query(RecipeProvider.getIngredUri(recipeId), null,
                null, null, null);

        //Show ingredients
        c.moveToFirst();
        titleTV.setText("INGREDIENTS (" + c.getInt(RecipesEntry.SERVINGS) + " servings)");

        ingredientsTV.setText(FormatUtils.formatIngredients(i));
        i.close();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        c.close();
    }
}
