package com.onval.bakingapp;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gval on 25/09/2017.
 */
public class RecipePresenterTest {
    @Test
    public void shouldPassRecipesToView() {
        //given
        RecipePresenter.View view = new MockView();
        IFetcher model = new MockModel();
        RecipePresenter presenter = new RecipePresenter(view, model);

        //when
        presenter.loadRecipes();

        //makes sure onAddRecipes is called
        Assert.assertTrue(((MockView) view).called);
    }

    private class MockView implements RecipePresenter.View {
        boolean called;

        @Override
        public void onNoInternetConnection() {
            // don't need this for now
        }

        @Override
        public void onAddRecipes(Set<Recipe> recipes) {
            called = true;
        }
    }

    private class MockModel implements IFetcher {
        @Override
        public Set<Recipe> fetchRecipes() {
            HashSet<Recipe> recipes = new HashSet<>();

            recipes.add(new Recipe());
            recipes.add(new Recipe());
            recipes.add(new Recipe());

            return recipes;
        }
    }
}