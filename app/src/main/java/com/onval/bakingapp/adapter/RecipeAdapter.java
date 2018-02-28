package com.onval.bakingapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.view.IRecipeView;
import com.squareup.picasso.Picasso;

import static com.onval.bakingapp.provider.RecipeContract.RecipesEntry;

/**
 * Created by gval on 27/09/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder>
implements View.OnClickListener
{
    private Context context;

    private Cursor recipes;
    private IRecipeView.Listener listener;

    public RecipeAdapter(Context context, IRecipeView.Listener listener) {
        this(context, null, listener);
    }

    public RecipeAdapter(Context context, Cursor recipes, IRecipeView.Listener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recipe, parent, false);

        final RecipeHolder holder = new RecipeHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                recipes.moveToPosition(position);
                listener.onRecipeClicked(
                        recipes.getInt(recipes.getColumnIndex(RecipesEntry._ID)));

                //todo: couldn't I just do: listener.onRecipeClicked(position) ???
            }
        });

        return holder;
    }

    @Override
    public int getItemCount() {
        if (recipes != null)
            return recipes.getCount();

        return 0;
    }

    @Override
    public void onBindViewHolder(final RecipeHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public void onClick(View view) {

    }

    public void addAllRecipes(Cursor recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        View view;
        TextView recipeName;
        TextView recipeServings;
        ImageView recipeImg;

        RecipeHolder (View view) {
            super(view);

            // Holds a reference to the view to be able to access it through the ViewHolder
            // in order to apply the on click listener from the onBindViewHolder method
            this.view = view;

            recipeName = (TextView) view.findViewById(R.id.recipe_name);
            recipeServings = (TextView) view.findViewById(R.id.recipe_servings);
            recipeImg = (ImageView) view.findViewById(R.id.recipe_img);
        }

        void bind(int position) {
            recipes.moveToPosition(position);

            recipeName.setText(recipes.getString(RecipesEntry.NAME));
            recipeServings.setText("Servings: " + recipes.getInt(RecipesEntry.SERVINGS));

            //So...the idea is that if the recipe has an image associated with it,
            //it will show in the cardview...and that would be pretty common in real
            //life situation (I hope)...but since in this small sample json no recipe
            //has an image...I decided to show a kitchen one, taken from google.
            //Kinda ugly but whatever.
            String imageUrl = recipes.getString(RecipesEntry.IMAGE);
            if (!imageUrl.equals("")) {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(recipeImg);
            }
            else {
                recipeImg.setImageResource(R.drawable.kitchen);
            }

//            close cursor after last element...actually I don't need to since
//            i'm using a CursorLoader
//            if (position == recipes.getCount())
//                recipes.close();
        }
    }
}
