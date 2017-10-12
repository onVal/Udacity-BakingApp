package com.onval.bakingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;
import com.onval.bakingapp.StepNotFoundException;
import com.onval.bakingapp.adapter.StepAdapter;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.data.Step;

import java.util.ArrayList;

import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;

public class StepDetailActivity extends AppCompatActivity
        implements StepDetailFragment.OnStepClickListener {

    private boolean twoPane;
    private FragmentTransaction ft;
    private Recipe recipe;
    private ArrayList<Step> steps;

    private StepAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (getResources().getBoolean(R.bool.isTablet))
            twoPane = true;

        ft = getSupportFragmentManager().beginTransaction();
        recipe = getIntent().getExtras().getParcelable(Recipe.RECIPE_PARCEL);

        //I set the step adapter in the activity to be able to control twopane
        adapter = new StepAdapter(this, recipe.getSteps(), this);

        steps = recipe.getSteps();

        if (twoPane) {
            ft.replace(R.id.step_detail_container, new StepDetailFragment())
                    .replace(R.id.frame_detail_container, DetailFragment.newInstance(steps, 0))
                    .commit();
        }
        else {
            ft.replace(R.id.step_detail_container, new StepDetailFragment())
                    .commit();
        }
    }

    //This method implements the master-detail pattern so it changes behavior based on device config
    @Override
    public void onStepClicked(int stepId) {
        int stepPosition = 0;

        try {
            stepPosition = StepAdapter.findStepPositionById(recipe.getSteps(), stepId);
        } catch (StepNotFoundException e) {e.printStackTrace();}

        if (twoPane) {
            ft.replace(R.id.frame_detail_container,
                    DetailFragment.newInstance(steps, stepPosition));
        }
        else {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(STEP_LIST_TAG, recipe.getSteps());
            intent.putExtra(STEP_POSITION_TAG, stepPosition);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    public StepAdapter getAdapter() {
        return adapter;
    }
}
