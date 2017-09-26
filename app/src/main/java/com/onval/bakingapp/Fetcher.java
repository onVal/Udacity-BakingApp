package com.onval.bakingapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by gval on 25/09/2017.
 */

public class Fetcher implements IFetcher {
    private Context context;

    private HashSet<Recipe> recipes;

    public Fetcher(Context context) {
        this.context = context;
        recipes = new HashSet<>();
    }

    @Override
    public void fetchRecipes(Response.Listener<JSONObject> responseListener,
                                    Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        recipes.clear();

        JsonObjectRequest request = new JsonObjectRequest(
                NetworkUtilities.RECIPE_URL,
                null,
                responseListener,
                errorListener
        );

        queue.add(request);
    }

    @Override
    public HashSet<Recipe> parseRecipes(JSONObject json) {
        return null;
    }
}
