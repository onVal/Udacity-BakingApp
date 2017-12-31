package com.onval.bakingapp;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.view.DetailActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;

/**
 * Created by gval on 04/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DetailRecipeUrlTest {

    ActivityTestRule<DetailActivity> testRule =
            new ActivityTestRule<>(DetailActivity.class, false, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(STEP_LIST_TAG, makeMockStepWithInvalidUrl());
        intent.putExtra(STEP_POSITION_TAG, 0);

        testRule.launchActivity(intent);
    }

    @Test
    public void testIfThumbnailIsShowingWhenInvalidVideoUrl() {
        onView(withId(R.id.thumbnail_img))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    private ArrayList<Step> makeMockStepWithInvalidUrl() {
        final String invalidThumbnailUrl =
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";

        ArrayList<Step> mockSteps = new ArrayList<>();
        mockSteps.add(new Step(0, "", "", "", invalidThumbnailUrl));

        return mockSteps;

    }
}