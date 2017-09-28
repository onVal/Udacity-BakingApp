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

            Log.d("RECIPE", name + " " + image + " " + servings);


            recipe = new Recipe.Builder(id, name)
                    .image(image)
                    .servings(servings)
                    .build();

            recipes.add(recipe);
        }

        return recipes;
    }
}
