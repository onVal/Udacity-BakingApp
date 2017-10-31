package com.onval.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.presenter.RecipePresenter;
import com.onval.bakingapp.view.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;

/**
 * Created by gval on 23/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ShowIngredientsTest {
    @Rule
    public IntentsTestRule<RecipeActivity> testRule =
            new IntentsTestRule<>(RecipeActivity.class);

    @Test
    public void checkIfIntentWorks() {
        registerIdlingResources(RecipePresenter.idlingResource);

        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        intended(hasExtraWithKey(RECIPE_PARCEL));
    }
}
