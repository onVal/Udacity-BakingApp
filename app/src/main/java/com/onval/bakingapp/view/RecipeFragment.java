package com.onval.bakingapp.view;


import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onval.bakingapp.R;
import com.onval.bakingapp.adapter.RecipeAdapter;
import com.onval.bakingapp.model.Fetcher;
import com.onval.bakingapp.presenter.IRecipePresenter;
import com.onval.bakingapp.presenter.RecipePresenter;
import com.onval.bakingapp.provider.RecipeContract;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment extends Fragment implements IRecipeView, IRecipeView.Listener {
    private IRecipePresenter presenter;
    private Context context;

    @BindView(R.id.recipes_recyclerview) RecyclerView recyclerView;

    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, root);

        context = getContext();

        //todo: should I use dependency injection for this?
        presenter = new RecipePresenter(context, this, new Fetcher(getActivity()));

        adapter = new RecipeAdapter(context, this);

        presenter.loadRecipes(); //calls addAllRecipes when finishes
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Use linearlayout on portrait, and gridlayout on landspace
        // (and on tablets, regardless of orientation)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                getResources().getBoolean(R.bool.isTablet))
            layoutManager = new GridLayoutManager(context, 3);


        else
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void displayRecipes() {
        Uri uri = RecipeContract.RecipesEntry.RECIPE_URI;

        CursorLoader loader = new CursorLoader(context, uri, null,
                    null, null, null);

        Cursor recipes = loader.loadInBackground();
        adapter.addAllRecipes(recipes);

//        c.close();
//        adapter.addAllRecipes(recipes);
//        adapter.notifyDataSetChanged();

        //this will be true only in testing
//        if (idlingResource != null)
//            idlingResource.decrement();
    }

    @Override
    public void displayErrorMsg(String msg) {
        Toast.makeText(context, msg,
                Toast.LENGTH_SHORT).show();
        Log.d("TAG", msg);
    }

    @Override
    public void onNoInternetConnection() {
        Toast.makeText(context, "Couldn't load recipes. No internet connection.",
                Toast.LENGTH_SHORT).show();
    }

    public void onRecipeClicked(int idRecipe) {
        Intent intent = new Intent(context, StepDetailActivity.class);

        //TODO: put this constant in a better place
        intent.putExtra("RECIPE_ID", idRecipe);

        startActivity(intent);
    }
}
