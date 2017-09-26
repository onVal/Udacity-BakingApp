package com.onval.bakingapp;

import com.android.volley.Response;

import org.json.JSONArray;

import java.util.HashSet;

/**
 * Created by gval on 25/09/2017.
 */

public interface IFetcher {
    void fetchFromServer(Response.Listener<JSONArray> responseListener,
                         Response.ErrorListener errorListener);

    HashSet<Recipe> parseRecipes(JSONArray json);
}
