package com.onval.bakingapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.onval.bakingapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, new DetailFragment())
                .commit();
    }
}
