package com.onval.bakingapp;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by gval on 25/09/2017.
 */

public interface IFetcher {
    void fetchRecipes(Response.Listener<JSONObject> responseListener,
                      Response.ErrorListener errorListener);

    HashSet<Recipe> parseRecipes(JSONObject json);
}
