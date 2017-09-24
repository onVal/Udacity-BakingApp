package com.onval.bakingapp;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gval on 25/09/2017.
 */
public class FetcherTest {
    @Test
    void testFetchRecipes() {
        MockFetcher mockFetcher = new MockFetcher();
//        recipe

    }

    private class MockFetcher implements IFetcher {
        @Override
        public Set<Recipe> fetchRecipes() {
            return new HashSet<>();
        }
    }

}