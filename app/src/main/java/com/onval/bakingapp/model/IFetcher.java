package com.onval.bakingapp.model;

import com.android.volley.Response;
import com.onval.bakingapp.data.Recipe;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by gval on 25/09/2017.
 */

public interface IFetcher {
    void fetchFromServer(Response.Listener<JSONArray> responseListener,
                         Response.ErrorListener errorListener);

    List<Recipe> parseRecipes(JSONArray json);
}
