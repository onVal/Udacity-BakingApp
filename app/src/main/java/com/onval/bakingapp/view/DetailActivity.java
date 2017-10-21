package com.onval.bakingapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;

import java.util.ArrayList;

import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        //Since I want the fragment itself to contain the arraylist of steps and the position
        //in order to manage the twopane option (when I don't create an activity for the fragment)
        //I 'transform' the intent extras into arguments to pass to the fragment
        ArrayList<Step> steps = getIntent().getExtras().getParcelableArrayList(STEP_LIST_TAG);
        int position = getIntent().getExtras().getInt(STEP_POSITION_TAG);

        setTitle(steps.get(position).getId() + ". " + steps.get(position).getShortDescription());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container,
                            DetailFragment.newInstance(steps, position))
                    .commit();
        }
    }
}
