package com.onval.bakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;
import com.onval.bakingapp.RecipePresenter;

import java.util.Set;


public class RecipeFragment extends Fragment implements RecipePresenter.View {

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onAddRecipes(Set<Recipe> recipes) {

    }
}
