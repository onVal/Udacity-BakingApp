package com.onval.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.presenter.RecipePresenter;
import com.onval.bakingapp.view.RecipeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;

/**
 * Created by gval on 23/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ShowIngredientsTest {
    @Rule
    public IntentsTestRule<RecipeActivity> testRule =
            new IntentsTestRule<>(RecipeActivity.class);

    @Before
    public void beforeEachTest() {
        registerIdlingResources(RecipePresenter.idlingResource);
    }

    @Test
    public void testIfIntentWorks() {

        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        intended(hasExtraWithKey(RECIPE_PARCEL));
    }

    @Test
    public void testIfIngredientsAreShowingCorrectly() {

        final String nutellaPieIngredients = "INGREDIENTS (for 8 people)\n" +
                "\t• Graham Cracker crumbs (2 CUP)\n" +
                "\t• unsalted butter, melted (6 TBLSP)\n" +
                "\t• granulated sugar (0 CUP)\n" +
                "\t• salt (1 TSP)\n" +
                "\t• vanilla (5 TBLSP)\n" +
                "\t• Nutella or other chocolate-hazelnut spread (1 K)\n" +
                "\t• Mascapone Cheese(room temperature) (500 G)\n" +
                "\t• heavy cream(cold) (1 CUP)\n" +
                "\t• cream cheese(softened) (4 OZ)";

        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.steps_ingredients))
                .check(matches(withText(nutellaPieIngredients)));
    }
}
