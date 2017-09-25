package com.onval.bakingapp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gval on 25/09/2017.
 */
public class RecipePresenterTest {

    RecipePresenter.View view;
    IFetcher model;
    RecipePresenter presenter;

    @Before
    public void setUp() {
        view = new MockView();

    }

    @Test
    public void shouldPassRecipesToView() {
        model = new MockModel(true);
        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        //makes sure onAddRecipes is called
        Assert.assertTrue(((MockView) view).addRecipesCalled);
    }

    @Test
    public void shouldHandleNoRecipes() {
        model = new MockModel(false);
        presenter = new RecipePresenter(view, model);

        presenter.loadRecipes();

        Assert.assertTrue(((MockView) view).noRecipesCalled);
    }



    private class MockView implements RecipePresenter.View {
        boolean addRecipesCalled, noRecipesCalled;

        @Override
        public void onNoInternetConnection() {
            // don't need this for now
        }

        @Override
        public void onAddRecipes(Set<Recipe> recipes) {
            if (recipes.size() == 3)
                addRecipesCalled = true;
        }

        @Override
        public void displayNoRecipe() {
            noRecipesCalled = true;
        }
    }

    private class MockModel implements IFetcher {
        boolean returnsSomething;

        MockModel(boolean returnsSomething) {
            this.returnsSomething = returnsSomething;
        }

        @Override
        public Set<Recipe> fetchRecipes() {
            HashSet<Recipe> recipes = new HashSet<>();

            if (returnsSomething) {
                recipes.add(new Recipe());
                recipes.add(new Recipe());
                recipes.add(new Recipe());
            }

            return recipes;
        }
    }
}