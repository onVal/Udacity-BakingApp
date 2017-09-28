package com.onval.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 27/09/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private Context context;

    private ArrayList<Recipe> recipes;
    private View.OnClickListener listener;


    public RecipeAdapter(Context context, List<Recipe> recipes, View.OnClickListener listener) {
        this.context = context;
        this.recipes = (ArrayList<Recipe>) recipes;
        this.listener = listener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recipe, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.bind(position);
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        TextView recipeName;

        RecipeHolder (View view) {
            super(view);

            recipeName = (TextView) view.findViewById(R.id.recipe_name);
            view.setOnClickListener(listener);
        }

        void bind(int position) {
            Recipe recipe = recipes.get(position);
            recipeName.setText(recipe.name);
        }
    }
}
