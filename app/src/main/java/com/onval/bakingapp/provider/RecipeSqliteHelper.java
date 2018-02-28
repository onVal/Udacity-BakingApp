package com.onval.bakingapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.onval.bakingapp.provider.RecipeContract.IngredientsEntry;
import static com.onval.bakingapp.provider.RecipeContract.RecipesEntry;
import static com.onval.bakingapp.provider.RecipeContract.StepsEntry;




/**
 * Created by gval on 31/12/2017.
 */

public class RecipeSqliteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "baking.db";
    private static final int DB_VERSION = 4;

    RecipeSqliteHelper(Context context) {
        super(context, DB_NAME, null,  DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String recipeTableSQL = "CREATE TABLE " + RecipesEntry.TABLE_NAME + " (" +
                RecipesEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                RecipesEntry.NAME_COLUMN + " TEXT NOT NULL," +
                RecipesEntry.SERVINGS_COLUMN + " INTEGER NOT NULL, " +
                RecipesEntry.IMAGE_COLUMN + " TEXT);";

        final String ingredientTableSQL = "CREATE TABLE " + RecipeContract.IngredientsEntry.TABLE_NAME + " (" +
                IngredientsEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                RecipeContract.IngredientsEntry.QUANTITY_COLUMN + " INT, " +
                RecipeContract.IngredientsEntry.MEASURE_COLUMN + " TEXT, " +
                IngredientsEntry.INGREDIENT_COLUMN + " TEXT, " +
                IngredientsEntry.RECIPE_ID_COLUMN + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + IngredientsEntry.RECIPE_ID_COLUMN + ") " +
                "REFERENCES " + RecipesEntry.TABLE_NAME + "(" + RecipesEntry._ID + "));";


        final String stepsTableSQL = "CREATE TABLE " + StepsEntry.TABLE_NAME + " (" +
                StepsEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                StepsEntry.SHORT_DESC_COLUMN + " TEXT NOT NULL, " +
                StepsEntry.DESC_COLUMN + " TEXT NOT NULL, " +
                StepsEntry.VIDEO_COLUMN + " TEXT, " +
                StepsEntry.THUMBNAIL_COLUMN + " TEXT , " +
                StepsEntry.RECIPE_ID_COLUMN + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + StepsEntry.RECIPE_ID_COLUMN + ") " +
                        "REFERENCES " + RecipesEntry.TABLE_NAME + "(" + RecipesEntry._ID + "));";

        sqLiteDatabase.execSQL(recipeTableSQL);
        sqLiteDatabase.execSQL(ingredientTableSQL);
        sqLiteDatabase.execSQL(stepsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String dropRecipe = "DROP TABLE IF EXISTS  " + RecipesEntry.TABLE_NAME + ";";
        final String dropIngredient = "DROP TABLE IF EXISTS  " + IngredientsEntry.TABLE_NAME + ";";
        final String dropStep = "DROP TABLE IF EXISTS  " + StepsEntry.TABLE_NAME + ";";

        sqLiteDatabase.execSQL(dropRecipe);
        sqLiteDatabase.execSQL(dropIngredient);
        sqLiteDatabase.execSQL(dropStep);

        onCreate(sqLiteDatabase);

        Log.d("DB_UPGRADE", "Upgrading db from version " + i + " to version " + i1);
    }
}
