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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.onval.bakingapp.TestUtils.TestUtilities.NUTELLAPIE_POSITION;

/**
 * Created by gval on 03/11/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ThumbnailTest {
    CountingIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<RecipeActivity> testRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setUp() {
        idlingResource = testRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void isThumbnailDisplayed() {
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(NUTELLAPIE_POSITION))
                .perform(RecyclerViewActions.actionOnItemAtPosition(NUTELLAPIE_POSITION, click()));

        onView(withId(R.id.steps_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.thumbnail_img))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(idlingResource);
    }
}
