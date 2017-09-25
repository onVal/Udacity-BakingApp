package com.onval.bakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onval.bakingapp.IRecipePresenter;
import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;
import com.onval.bakingapp.RecipePresenter;

import java.util.Set;


public class RecipeFragment extends Fragment implements RecipePresenter.View {
    IRecipePresenter presenter;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        presenter = new RecipePresenter(this, null);
        presenter.loadRecipes();

        return root;
    }

    @Override
    public void onAddRecipes(Set<Recipe> recipes) {
        //todo: to implement
        Toast.makeText(getContext(), "Adding recipes...!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoInternetConnection() {
        Toast.makeText(getContext(), "Couldn't load recipes. No internet connection.",
                Toast.LENGTH_SHORT).show();
    }
}
