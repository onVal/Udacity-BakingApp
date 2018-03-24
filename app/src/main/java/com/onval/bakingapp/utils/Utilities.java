package com.onval.bakingapp.utils;

import android.database.Cursor;

import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.provider.RecipeContract.StepsEntry;

import java.util.ArrayList;

/**
 * Created by gval on 05/03/2018.
 */

public class Utilities {
    public static ArrayList<Step> cursorToStepArray(Cursor c) {
        ArrayList<Step> steps = new ArrayList<>();

        while (c.moveToNext()) {
            steps.add(new Step(
                    c.getInt(StepsEntry.ID),
                    c.getString(StepsEntry.SHORT_DESC),
                    c.getString(StepsEntry.DESC),
                    c.getString(StepsEntry.VIDEO),
                    c.getString(StepsEntry.THUMBNAIL))
            );
        }

        return steps;
    }
}
