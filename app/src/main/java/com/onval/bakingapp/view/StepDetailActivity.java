package com.onval.bakingapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;
import com.onval.bakingapp.adapter.StepAdapter;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.provider.RecipeContract.RecipesEntry;
import com.onval.bakingapp.provider.RecipeProvider;
import com.onval.bakingapp.utils.Utilities;

import java.util.ArrayList;

import static com.onval.bakingapp.view.StepDetailFragment.RECIPE_ID_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;

public class StepDetailActivity extends AppCompatActivity
        implements StepDetailFragment.OnStepClickListener {

    private static final String SELECTED_ITEM_POS = "selected-item-position";
    private boolean twoPane;

    private int recipeId;
    private Cursor steps;
    private ArrayList<Step> stepList;


    private StepAdapter stepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (getResources().getBoolean(R.bool.isTablet))
            twoPane = true;


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        recipeId = getIntent().getExtras().getInt(RECIPE_ID_TAG);

        steps = getContentResolver().query(RecipeProvider.getRecipeStepUri(recipeId),
                null, null, null, null);

        stepList = Utilities.cursorToStepArray(steps);


        Cursor recipeCursor = getContentResolver().query(RecipesEntry.RECIPE_URI,
                null,
                RecipesEntry._ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null);

        recipeCursor.moveToFirst();
        setTitle(recipeCursor.getString(RecipesEntry.NAME));
        recipeCursor.close();

        //this restores selected item upon config. changes
        int restoredItemPosition = (savedInstanceState != null) ?
                savedInstanceState.getInt(SELECTED_ITEM_POS) : 0;

        //I set the step stepAdapter in the activity to be able to control twopane
        stepAdapter = new StepAdapter(this, steps, this, restoredItemPosition);

        if (savedInstanceState == null) {
            if (twoPane) {
                ft.replace(R.id.step_detail_container, new StepDetailFragment())
                        .replace(R.id.frame_detail_container,
                                DetailFragment.newInstance(stepList, restoredItemPosition))
                        .commit();
            } else {
                ft.replace(R.id.step_detail_container, new StepDetailFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_ITEM_POS, stepAdapter.getSelectedItem());
    }

    //This method implements the master-detail pattern so it changes behavior based on device config
    @Override
    public void onStepClicked(int stepPosition) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (twoPane) {
            ft.replace(R.id.frame_detail_container, DetailFragment.newInstance(stepList, stepPosition))
                    .commit();
        }
        else {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(STEP_LIST_TAG, stepList);
            intent.putExtra(STEP_POSITION_TAG, stepPosition);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    public StepAdapter getStepAdapter() {
        return stepAdapter;
    }
}
