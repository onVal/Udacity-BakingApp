package com.onval.bakingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;
import com.onval.bakingapp.adapter.StepAdapter;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.data.Step;

import java.util.ArrayList;

import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;

public class StepDetailActivity extends AppCompatActivity
        implements StepDetailFragment.OnStepClickListener {

    private static final String SELECTED_ITEM_POS = "selected-item-position";
    private boolean twoPane;
    private ArrayList<Step> steps;

    private StepAdapter stepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (getResources().getBoolean(R.bool.isTablet))
            twoPane = true;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Recipe recipe = getIntent().getExtras().getParcelable(Recipe.RECIPE_PARCEL);
        steps = recipe.getSteps();

        //this restores selected item upon config. changes
        int restoredItemPosition = (savedInstanceState != null) ?
                savedInstanceState.getInt(SELECTED_ITEM_POS) : 0;

        //I set the step stepAdapter in the activity to be able to control twopane
        stepAdapter = new StepAdapter(this, steps, this, restoredItemPosition);

        if (twoPane) {
            ft.replace(R.id.step_detail_container, new StepDetailFragment())
                    .replace(R.id.frame_detail_container,
                            DetailFragment.newInstance(steps, restoredItemPosition))
                    .commit();
        }
        else {
            ft.replace(R.id.step_detail_container, new StepDetailFragment())
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_ITEM_POS, stepAdapter.getSelectedItem());
    }

    //This method implements the master-detail pattern so it changes behavior based on device config
    @Override
    public void onStepClicked(int stepId) {
        int stepPosition = StepAdapter.findStepPositionById(steps, stepId);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (stepPosition == -1)
            return;

        if (twoPane) {
            ft.replace(R.id.frame_detail_container, DetailFragment.newInstance(steps, stepPosition))
                    .commit();
        }
        else {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(STEP_LIST_TAG, steps);
            intent.putExtra(STEP_POSITION_TAG, stepPosition);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    public StepAdapter getStepAdapter() {
        return stepAdapter;
    }
}
