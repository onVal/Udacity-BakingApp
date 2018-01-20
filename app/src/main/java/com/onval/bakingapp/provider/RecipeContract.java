package com.onval.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gval on 31/12/2017.
 */

public class RecipeContract {
    public static final String AUTHORITY = "com.onval.bakingapp.provider.RecipeProvider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static class RecipesTable implements BaseColumns {
        public static final String NAME = "recipe";

        public static final String NAME_COLUMN = "name";
        public static final String SERVINGS_COLUMN = "servings";
        public static final String IMAGE_COLUMN = "image";

        //Uri for accessing recipe list
        public static final Uri RECIPE_URI =
                BASE_URI.buildUpon().appendPath(NAME).build();

    }

    public static class IngredientsTable implements BaseColumns {
        public static final String NAME = "ingredient";

        public static final String QUANTITY_COLUMN = "quantity";
        public static final String MEASURE_COLUMN = "measure";
        public static final String INGREDIENT_COLUMN = "name";
        public static final String RECIPE_ID_COLUMN = "recipe_id";

        //Uri for accessing ingredient list
        public static final Uri INGREDIENTS_URI =
                BASE_URI.buildUpon().appendPath(NAME).build();
    }

    public static class StepsTable implements BaseColumns {
        public static final String NAME = "step";

        public static final String SHORT_DESC_COLUMN = "short_description";
        public static final String DESC_COLUMN = "description";
        public static final String VIDEO_COLUMN = "video_url";
        public static final String THUMBNAIL_COLUMN = "thumbnail_url";
        public static final String RECIPE_ID_COLUMN = "recipe_id";

        //Uri for accessing steps list
        public static final Uri STEPS_URI =
                BASE_URI.buildUpon().appendPath(NAME).build();
    }
}
