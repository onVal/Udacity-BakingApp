package com.onval.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.TestUtils.TestUtilities;
import com.onval.bakingapp.view.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.onval.bakingapp.TestUtils.RecyclerViewMatcher.withRecyclerView;
import static com.onval.bakingapp.TestUtils.TestUtilities.BROWNIES_POSITION;

/**
 * Created by gval on 03/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {

    @Rule
    public ActivityTestRule<RecipeActivity> testRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setUp() {
        TestUtilities.setupCountingIdlingResource();
    }

    @Test
    public void isRecipeTitleShowing() {
        onView(withRecyclerView(R.id.recipes_recyclerview).atPosition(BROWNIES_POSITION))
                .check(matches(hasDescendant(withText("Brownies"))));
    }

    @After
    public void tearDown() {
        TestUtilities.teardownCountingIdlingResource();
    }

}
