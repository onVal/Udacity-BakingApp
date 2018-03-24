package com.onval.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gval on 31/12/2017.
 */

public class RecipeContract {
    public static final String AUTHORITY = "com.onval.bakingapp.provider.RecipeProvider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static class RecipesEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipe";

        public static final String NAME_COLUMN = "name";
        public static final String SERVINGS_COLUMN = "servings";
        public static final String IMAGE_COLUMN = "image";

        //Short way to access columns easily
        public static final int ID = 0;
        public static final int NAME = 1;
        public static final int SERVINGS = 2;
        public static final int IMAGE = 3;

        //Uri for accessing recipe list
        public static final Uri RECIPE_URI =
                BASE_URI.buildUpon().appendPath(TABLE_NAME).build();

    }

    public static class IngredientsEntry implements BaseColumns {
        public static final String TABLE_NAME = "ingredient";

        public static final String QUANTITY_COLUMN = "quantity";
        public static final String MEASURE_COLUMN = "measure";
        public static final String INGREDIENT_COLUMN = "name";
        public static final String RECIPE_ID_COLUMN = "recipe_id";

        //Short way to access columns easily
        public static final int ID = 0;
        public static final int QUANTITY = 1;
        public static final int MEASURE = 2;
        public static final int INGREDIENT = 3;
        public static final int RECIPE_ID = 4;


        //Uri for accessing ingredient list
        public static final Uri INGREDIENTS_URI =
                BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
    }

    public static class StepsEntry implements BaseColumns {
        public static final String TABLE_NAME = "step";

        public static final String SHORT_DESC_COLUMN = "short_description";
        public static final String DESC_COLUMN = "description";
        public static final String VIDEO_COLUMN = "video_url";
        public static final String THUMBNAIL_COLUMN = "thumbnail_url";
        public static final String RECIPE_ID_COLUMN = "recipe_id";

        //Short way to access columns easily
        public static final int ID = 0;
        public static final int SHORT_DESC = 1;
        public static final int DESC = 2;
        public static final int VIDEO = 3;
        public static final int THUMBNAIL = 4;
        public static final int RECIPE_ID = 5;


        //Uri for accessing steps list
        public static final Uri STEPS_URI =
                BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
    }
}
