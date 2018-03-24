package com.onval.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.TestUtils.TestUtilities;
import com.onval.bakingapp.view.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.onval.bakingapp.view.StepDetailFragment.RECIPE_ID_TAG;

/**
 * Created by gval on 03/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeIntentTest {

    @Rule
    public IntentsTestRule<RecipeActivity> testRule =
            new IntentsTestRule<>(RecipeActivity.class);

    @Before
    public void setUp() {
        TestUtilities.setupCountingIdlingResource();

    }

    @Test
    public void doesIntentWork() {

        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        intended(hasExtraWithKey(RECIPE_ID_TAG));
    }

    @After
    public void tearDown() {
        TestUtilities.teardownCountingIdlingResource();
    }
}
