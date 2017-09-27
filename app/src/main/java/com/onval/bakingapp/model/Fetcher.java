package com.onval.bakingapp.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.onval.bakingapp.NetworkUtilities;
import com.onval.bakingapp.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

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
    public HashSet<Recipe> parseRecipes(JSONArray json) {
        HashSet<Recipe> recipes = new HashSet<>();
        Recipe recipe;

        for (int i = 0; ; i++) {
            JSONObject current = json.optJSONObject(i);

            if (current == null)
                break;

            String name = current.optString("name");
            String image = current.optString("image");
            int servings = current.optInt("servings");

            Log.d("RECIPE", name + " " + image + " " + servings);
            recipe = new Recipe(name, image, servings);
            recipes.add(recipe);
        }

        return recipes;
    }
}
