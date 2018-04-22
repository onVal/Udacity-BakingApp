package com.onval.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.view.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.onval.bakingapp.TestUtils.RecyclerViewMatcher.withRecyclerView;
import static com.onval.bakingapp.TestUtils.TestUtilities.BROWNIES_POSITION;
import static com.onval.bakingapp.TestUtils.TestUtilities.NUTELLAPIE_POSITION;

/**
 * Created by gval on 03/11/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {
    private CountingIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<RecipeActivity> testRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setUp() {
        idlingResource = testRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void isRecipeTitleShowing() {
        onView(withRecyclerView(R.id.recipes_recyclerview).atPosition(BROWNIES_POSITION))
                .check(matches(hasDescendant(withText("Brownies"))));
    }

    @Test
    public void areIngredientsShowingCorrectly() {

        final String nutellaPieIngredients =
                "• Graham Cracker crumbs (2 CUP)\n" +
                        "• unsalted butter, melted (6 TBLSP)\n" +
                        "• granulated sugar (0 CUP)\n" +
                        "• salt (1 TSP)\n" +
                        "• vanilla (5 TBLSP)\n" +
                        "• Nutella or other chocolate-hazelnut spread (1 K)\n" +
                        "• Mascapone Cheese(room temperature) (500 G)\n" +
                        "• heavy cream(cold) (1 CUP)\n" +
                        "• cream cheese(softened) (4 OZ)";

        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(NUTELLAPIE_POSITION, click()));

        onView(withId(R.id.steps_ingredients))
                .check(matches(withText(nutellaPieIngredients)));
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(idlingResource);
    }
}
