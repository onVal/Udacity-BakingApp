package com.onval.bakingapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;

import static com.onval.bakingapp.presenter.RecipePresenter.IDLING_RESOURCE_TAG;

public class RecipeActivity extends AppCompatActivity {

    @Nullable
    private CountingIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

//    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public CountingIdlingResource getIdlingResource() {
        if (idlingResource == null)
            idlingResource = new CountingIdlingResource(IDLING_RESOURCE_TAG);

        return idlingResource;
    }
}
