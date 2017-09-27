package com.onval.bakingapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;
import com.onval.bakingapp.RecipeAdapter;
import com.onval.bakingapp.model.Fetcher;
import com.onval.bakingapp.presenter.IRecipePresenter;
import com.onval.bakingapp.presenter.RecipePresenter;

import java.util.List;


public class RecipeFragment extends Fragment implements IView {
    private IRecipePresenter presenter;

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private LinearLayoutManager layoutManager;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        //todo: should I use dependency injection for this?
        presenter = new RecipePresenter(this, new Fetcher(getActivity()));

        if (recyclerView == null) {
            recyclerView = (RecyclerView) root.findViewById(R.id.recipes_recyclerview);
            presenter.loadRecipes();
        }

        return root;
    }

    @Override
    public void onAddRecipes(List<Recipe> recipes) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new RecipeAdapter(getContext(), recipes);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayErrorMsg(String msg) {
        Toast.makeText(getContext(), msg,
                Toast.LENGTH_SHORT).show();
        Log.d("TAG", msg);
    }

    @Override
    public void onNoInternetConnection() {
        Toast.makeText(getContext(), "Couldn't load recipes. No internet connection.",
                Toast.LENGTH_SHORT).show();
    }
}
