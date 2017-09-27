package com.onval.bakingapp;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.bakingapp.model.IFetcher;
import com.onval.bakingapp.presenter.RecipePresenter;
import com.onval.bakingapp.view.IView;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gval on 25/09/2017.
 */
public class RecipePresenterTest {

    IView view;
    IFetcher model;
    RecipePresenter presenter;

    //These tests make no more sense
    @Before
    public void setUp() {

    }

    @Test
    public void shouldPassRecipesToView() {
        view = new MockView();
        model = new MockFetcher();

        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        Assert.assertEquals(4, ((MockView) view).numOfRecipes);

    }

    @Test
    public void shouldHandleNoRecipes() {
        view = new MockView();
        model = new NoFetcher();

        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        Assert.assertTrue(((MockView) view).displayErrorMsgCalled);
    }

    //Mock classes
    private class MockView implements IView {
        int numOfRecipes;
        boolean displayErrorMsgCalled;

        @Override
        public void onAddRecipes(Set<Recipe> recipes) {
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
        public HashSet<Recipe> parseRecipes(JSONArray json) {
            HashSet<Recipe> recipes = new HashSet<>();

            recipes.add(new Recipe("cake", "", 3));
            recipes.add(new Recipe("meat", "", 5));
            recipes.add(new Recipe("fish", "", 7));
            recipes.add(new Recipe("rice", "", 9));

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
        public HashSet<Recipe> parseRecipes(JSONArray json) {
            return null;
        }
    }

}