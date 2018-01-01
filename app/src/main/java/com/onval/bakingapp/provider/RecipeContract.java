package com.onval.bakingapp.provider;

import android.provider.BaseColumns;

/**
 * Created by gval on 31/12/2017.
 */

public class RecipeContract {
    public static final String DB_NAME = "recipe.db";
    public static final int DB_VERSION = 1;

    public static class RecipesTable implements BaseColumns {
        public static final String NAME = "recipe";

        public static final String NAME_COLUMN = "name";
        public static final String SERVINGS_COLUMN = "servings";
        public static final String IMAGE_COLUMN = "image";
    }

    public static class IngredientsTable implements BaseColumns {
        public static final String NAME = "ingredient";

        public static final String QUANTITY_COLUMN = "quantity";
        public static final String MEASURE_COLUMN = "measure";
        public static final String INGREDIENT_COLUMN = "ingredient";
        public static final String RECIPE_ID_COLUMN = "recipe_id";
    }

    public static class StepsTable implements BaseColumns {
        public static final String NAME = "step";

        public static final String SHORT_DESC_COLUMN = "short_description";
        public static final String DESC_COLUMN = "description";
        public static final String VIDEO_COLUMN = "video_url";
        public static final String THUMBNAIL_COLUMN = "thumbnail_url";
        public static final String RECIPE_ID_COLUMN = "recipe_id";
    }
}
