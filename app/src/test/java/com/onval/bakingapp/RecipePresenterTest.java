package com.onval.bakingapp;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.model.IFetcher;
import com.onval.bakingapp.presenter.RecipePresenter;
import com.onval.bakingapp.view.IRecipeView;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 25/09/2017.
 */
public class RecipePresenterTest {

    IRecipeView view;
    IFetcher model;
    RecipePresenter presenter;

    //These tests make no more sense
    @Before
    public void setUp() {

    }

    @Test
    public void shouldPassRecipesToView() {
        view = new MockRecipeView();
        model = new MockFetcher();

        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        Assert.assertEquals(5, ((MockRecipeView) view).numOfRecipes);

    }

    @Test
    public void shouldHandleNoRecipes() {
        view = new MockRecipeView();
        model = new NoFetcher();

        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        Assert.assertTrue(((MockRecipeView) view).displayErrorMsgCalled);
    }

    //Mock classes
    private class MockRecipeView implements IRecipeView {
        int numOfRecipes;
        boolean displayErrorMsgCalled;

        @Override
        public void addRecipes(List<Recipe> recipes) {
            numOfRecipes = recipes.size();
        }

        @Override
        public void displayErrorMsg(String msg) {
            displayErrorMsgCalled = true;
        }

        @Override
        public void onNoInternetConnection() {

        }
    }

    private class MockFetcher implements IFetcher {
        @Override
        public void fetchFromServer(Response.Listener<JSONArray> responseListener,
                                    Response.ErrorListener errorListener) {
            responseListener.onResponse(new JSONArray());
        }

        @Override
        public ArrayList<Recipe> parseRecipes(JSONArray json) {
            ArrayList<Recipe> recipes = new ArrayList<>();

            recipes.add(new Recipe.Builder(0, "cake").build());
            recipes.add(new Recipe.Builder(4, "fish").build());
            recipes.add(new Recipe.Builder(2, "meat").build());
            recipes.add(new Recipe.Builder(9, "coke").build());
            recipes.add(new Recipe.Builder(3, "eggs").build());

            return recipes;
        }
    }

    private class NoFetcher implements IFetcher {
        @Override
        public void fetchFromServer(Response.Listener<JSONArray> responseListener,
                                    Response.ErrorListener errorListener) {
            errorListener.onErrorResponse(new VolleyError());
        }

        @Override
        public ArrayList<Recipe> parseRecipes(JSONArray json) {
            return null;
        }
    }

}