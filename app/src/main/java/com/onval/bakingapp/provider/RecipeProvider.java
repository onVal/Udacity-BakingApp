package com.onval.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.onval.bakingapp.data.Ingredient;
import com.onval.bakingapp.data.Recipe;
import com.onval.bakingapp.data.Step;

import static com.onval.bakingapp.provider.RecipeContract.AUTHORITY;
import static com.onval.bakingapp.provider.RecipeContract.IngredientsEntry;
import static com.onval.bakingapp.provider.RecipeContract.RecipesEntry;
import static com.onval.bakingapp.provider.RecipeContract.StepsEntry;




/**
 * Created by gval on 01/01/2018.
 */

public class RecipeProvider extends ContentProvider {
    private SQLiteOpenHelper helper;

    //access the recipe/step/ingredient tables
    private static final int RECIPE_CODE = 100;
    private static final int STEP_CODE = 200;
    private static final int INGREDIENT_CODE = 300;

    //access all ingredients of a specific recipe
    private static final int RECIPE_INGRED_CODE = 400;

    //access step list (description) of a specific recipe
    private static final int RECIPE_STEP_CODE = 500;

    //access a specific step of a specific recipe
    private static final int SINGLE_STEP_CODE  = 501;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "/recipe", RECIPE_CODE);
        uriMatcher.addURI(AUTHORITY, "/step", STEP_CODE);
        uriMatcher.addURI(AUTHORITY, "/ingredient", INGREDIENT_CODE);
        uriMatcher.addURI(AUTHORITY, "/recipe/#/ingredient", RECIPE_INGRED_CODE);
        uriMatcher.addURI(AUTHORITY, "/recipe/#/step/description", RECIPE_STEP_CODE);
        uriMatcher.addURI(AUTHORITY, "/recipe/#/step/#", SINGLE_STEP_CODE);
    }

    @Override
    public boolean onCreate() {
        helper = new RecipeSqliteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        SQLiteOpenHelper helper = new RecipeSqliteHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c;

        switch (uriMatcher.match(uri)) {
            case RECIPE_CODE:
                c = db.query(RecipesEntry.TABLE_NAME,
                        null, null, null,
                        null, null, null);
                break;
            case INGREDIENT_CODE:
                c = db.query(RecipeContract.IngredientsEntry.TABLE_NAME,
                        null, null, null,
                        null, null, null);
                break;
            case STEP_CODE:
                c = db.query(StepsEntry.TABLE_NAME,
                        null, null, null,
                        null, null, null);
                break;
            default:
                c = null;
        }

        return c;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteOpenHelper helper = new RecipeSqliteHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        long rowId;
        Uri rowUri;
        String table;

        switch (uriMatcher.match(uri)) {
            case RECIPE_CODE:
                table = RecipesEntry.TABLE_NAME;
                break;
            case INGREDIENT_CODE:
                table = IngredientsEntry.TABLE_NAME;
                break;
            case STEP_CODE:
                table = StepsEntry.TABLE_NAME;
                break;

            default:
                return null;
        }

        rowId = db.insert(table, null, contentValues);

        if (rowId != -1)
            rowUri = uri.buildUpon()
                    .appendPath(table)
                    .appendPath(String.valueOf(rowId))
                    .build();
        else
            rowUri = null;

        getContext().getContentResolver().notifyChange(rowUri, null);
        return rowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteOpenHelper helper = new RecipeSqliteHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        int row;
        switch(uriMatcher.match(uri)) {
            case RECIPE_CODE:
                row = db.delete(RecipesEntry.TABLE_NAME, "1", null);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                row = 0;
                break;
        }
        return row;
    }

    public static void insertRecipe(ContentResolver resolver, Recipe r) {

        //convert recipe into contentvalues and put it in provider
        ContentValues recipeValues = new ContentValues();

        recipeValues.put(RecipesEntry.NAME_COLUMN, r.getName());
        recipeValues.put(RecipesEntry.SERVINGS_COLUMN, r.getServingsNum());
        recipeValues.put(RecipesEntry.IMAGE_COLUMN, r.getImagePath());

        Uri recipeUri = resolver.insert(RecipesEntry.RECIPE_URI, recipeValues);

        //get the recipe_id from recipe uri
        int recipeId = Integer.parseInt(recipeUri.getLastPathSegment());
        Log.d("WOW", "recipe id = " + recipeId);

        //extract ingredient from recipe into contentvalues and put it in provider
        ContentValues ingredValues = new ContentValues();

        for (Ingredient i : r.getIngredients()) {
            ingredValues.put(RecipeContract.IngredientsEntry.QUANTITY_COLUMN, i.getQuantity());
            ingredValues.put(RecipeContract.IngredientsEntry.MEASURE_COLUMN, i.getMeasure());
            ingredValues.put(RecipeContract.IngredientsEntry.INGREDIENT_COLUMN, i.getName());
            ingredValues.put(RecipeContract.IngredientsEntry.RECIPE_ID_COLUMN, recipeId);

            resolver.insert(RecipeContract.IngredientsEntry.INGREDIENTS_URI, ingredValues);
        }

        //extract steps from recipe into content values and put it in provider
        ContentValues stepValues = new ContentValues();

        for (Step s : r.getSteps()) {
            stepValues.put(StepsEntry.SHORT_DESC_COLUMN, s.getShortDescription());
            stepValues.put(StepsEntry.DESC_COLUMN, s.getDescription());
            stepValues.put(StepsEntry.VIDEO_COLUMN, s.getVideoURL());
            stepValues.put(StepsEntry.THUMBNAIL_COLUMN, s.getThumbnailURL());
            stepValues.put(StepsEntry.RECIPE_ID_COLUMN, recipeId);

            resolver.insert(StepsEntry.STEPS_URI, stepValues);
        }

    }
}
