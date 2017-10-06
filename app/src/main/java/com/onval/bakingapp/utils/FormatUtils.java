package com.onval.bakingapp.utils;

import android.support.annotation.NonNull;

/**
 * Created by gval on 06/10/2017.
 */

public class FormatUtils {
    @NonNull
    public static String formatStepInstructions(String instructions) {
        instructions = instructions.replaceFirst("\\.", ")"); //deal with first number

        String[] separatedInstructions = instructions.split("\\.");
        StringBuilder builder = new StringBuilder();

        for (String str : separatedInstructions) {
            builder.append(str).append(".\n");
        }

        return builder.toString();
    }
}
