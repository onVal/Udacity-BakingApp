package com.onval.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.view.StepDetailActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.onval.bakingapp.TestUtils.RecyclerViewMatcher.withRecyclerView;
import static com.onval.bakingapp.data.Recipe.RECIPE_PARCEL;

/**
 * Created by gval on 03/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailFragmentTest {

    @Rule
    public ActivityTestRule<StepDetailActivity> testRule =
            new ActivityTestRule<>(StepDetailActivity.class, false, false);


    @Before
    public void setUp() {
        //pass a mock recipe to the activity
        Intent intent = new Intent();
        intent.putExtra(RECIPE_PARCEL, mockRecipe());
        testRule.launchActivity(intent);
    }

    @Test
    public void isStepListShowingCorrectValue() {

        onView(withId(R.id.steps_ingredients));

        onView(withRecyclerView(R.id.steps_recyclerview).atPosition(0))
                .check(matches(withText("0. Zero")));

        onView(withRecyclerView(R.id.steps_recyclerview).atPosition(1))
                .check(matches(withText("1. One")));

        onView(withRecyclerView(R.id.steps_recyclerview).atPosition(2))
                .check(matches(withText("2. Two")));
    }

    private Recipe mockRecipe() {
        ArrayList<Step> mockSteps = new ArrayList<>();
        ArrayList<Ingredient> mockIngredients = new ArrayList<>();

        mockIngredients.add(new Ingredient("Yog", "oz", 20));

        mockSteps.add(new Step(0, "Zero", "", "", ""));
        mockSteps.add(new Step(1, "One", "", "", ""));
        mockSteps.add(new Step(2, "Two", "", "", ""));


        return new Recipe.Builder(0, "Yogurt")
                .steps(mockSteps)
                .ingredients(mockIngredients)
                .build();
    }
}
