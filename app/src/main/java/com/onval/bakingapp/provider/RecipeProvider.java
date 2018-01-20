package com.onval.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.onval.bakingapp.data.Recipe;

import static com.onval.bakingapp.provider.RecipeContract.AUTHORITY;

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
                c = db.query(RecipeContract.RecipesTable.NAME,
                        null, null, null,
                        null, null, null);
                break;
            case STEP_CODE:
                c = db.query(RecipeContract.StepsTable.NAME,
                        null, null, null,
                        null, null, null);
                break;
            case INGREDIENT_CODE:
                c = db.query(RecipeContract.IngredientsTable.NAME,
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
                table = RecipeContract.RecipesTable.NAME;
                break;
            case STEP_CODE:
                table = RecipeContract.StepsTable.NAME;
                break;
            case INGREDIENT_CODE:
                table = RecipeContract.IngredientsTable.NAME;
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

        //todo: should i call notifyChange ?
        return rowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteOpenHelper helper = new RecipeSqliteHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();


        return 0;
    }

    public static void insertRecipe(Context context, Recipe recipe) {
        //convert recipe into contentvalues

//        resolver.insert(recipe);
//        resolver.insert(ingredient);
//        resolver.insert(steps);

    }
}
