package com.onval.bakingapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onval.bakingapp.R;

public class StepDetailActivity extends AppCompatActivity {
    public static final String STEPDETAIL_FRAGMENT_TAG = "stepdetail-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        //I use replace so the activity doesn't instantiate multiple fragments
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, new StepDetailFragment())
                .commit();
    }
}
