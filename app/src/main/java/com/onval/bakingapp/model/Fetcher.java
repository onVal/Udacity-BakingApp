package com.onval.bakingapp.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.onval.bakingapp.Ingredient;
import com.onval.bakingapp.NetworkUtilities;
import com.onval.bakingapp.Recipe;
import com.onval.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 25/09/2017.
 */

public class Fetcher implements IFetcher {
    private Context context;

    public Fetcher(Context context) {
        this.context = context;
    }

    @Override
    public void fetchFromServer(Response.Listener<JSONArray> responseListener,
                                Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(
                NetworkUtilities.RECIPE_URL,
                responseListener,
                errorListener
        );

        queue.add(request);
    }

    @Override
    public List<Recipe> parseRecipes(JSONArray json) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Recipe recipe;

        for (int i = 0; ; i++) {
            JSONObject current = json.optJSONObject(i);

            if (current == null)
                break;

            int id = current.optInt("id");
            String name = current.optString("name");
            String image = current.optString("image");
            int servings = current.optInt("servings");

            List<Ingredient> ingredients = parseIngredients(current.optJSONArray("ingredients"));
            List<Step> steps = parseSteps(current.optJSONArray("steps"));

            recipe = new Recipe.Builder(id, name)
                    .ingredients((ArrayList<Ingredient>) ingredients)
                    .steps((ArrayList<Step>) steps)
                    .image(image)
                    .servings(servings)
                    .build();

            recipes.add(recipe);
        }

        return recipes;
    }

    private List<Ingredient> parseIngredients(JSONArray json) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; ; i++) {
            JSONObject current = json.optJSONObject(i);

            if (current == null)
                break;

            String name = current.optString("ingredient");
            String measure = current.optString("measure");
            int quantity = current.optInt("quantity");

            Ingredient e = new Ingredient(name, measure, quantity);
            ingredients.add(e);
        }

        return ingredients;
    }

    //todo: finish this implementation
    private List<Step> parseSteps(JSONArray json) {
        ArrayList<Step> steps = new ArrayList<>();

        for (int i = 0; ; i++) {
            JSONObject current = json.optJSONObject(i);

            if (current == null)
                break;

            int id = current.optInt("id");
            String shortDescription = current.optString("shortDescription");
            String description = current.optString("description");
            String videoURL = current.optString("videoURL");
            String thumbnailURL = current.optString("thumbnailURL");

            steps.add(new Step(id, shortDescription, description, videoURL, thumbnailURL));
        }

        return steps;
    }
}
