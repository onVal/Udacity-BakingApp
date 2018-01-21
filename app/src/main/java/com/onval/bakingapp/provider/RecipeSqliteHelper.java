package com.onval.bakingapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.onval.bakingapp.provider.RecipeContract.IngredientsTable;
import static com.onval.bakingapp.provider.RecipeContract.RecipesTable;
import static com.onval.bakingapp.provider.RecipeContract.StepsTable;




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
        final String recipeTableSQL = "CREATE TABLE " + RecipesTable.NAME + " (" +
                RecipesTable._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                RecipesTable.NAME_COLUMN + " TEXT NOT NULL," +
                RecipesTable.SERVINGS_COLUMN + " INTEGER NOT NULL, " +
                RecipesTable.IMAGE_COLUMN + " TEXT);";

        final String ingredientTableSQL = "CREATE TABLE " + IngredientsTable.NAME + " (" +
                IngredientsTable._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                IngredientsTable.QUANTITY_COLUMN + " INT, " +
                IngredientsTable.MEASURE_COLUMN + " TEXT, " +
                IngredientsTable.INGREDIENT_COLUMN + " TEXT, " +
                IngredientsTable.RECIPE_ID_COLUMN + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + IngredientsTable.RECIPE_ID_COLUMN + ") " +
                "REFERENCES " + RecipesTable.NAME + "(" + RecipesTable._ID + "));";


        final String stepsTableSQL = "CREATE TABLE " + StepsTable.NAME + " (" +
                StepsTable._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                StepsTable.SHORT_DESC_COLUMN + " TEXT NOT NULL, " +
                StepsTable.DESC_COLUMN + " TEXT NOT NULL, " +
                StepsTable.VIDEO_COLUMN + " TEXT, " +
                StepsTable.THUMBNAIL_COLUMN + " TEXT , " +
                StepsTable.RECIPE_ID_COLUMN + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + StepsTable.RECIPE_ID_COLUMN + ") " +
                        "REFERENCES " + RecipesTable.NAME + "(" + RecipesTable._ID + "));";

        sqLiteDatabase.execSQL(recipeTableSQL);
        sqLiteDatabase.execSQL(ingredientTableSQL);
        sqLiteDatabase.execSQL(stepsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String dropRecipe = "DROP TABLE IF EXISTS  " + RecipesTable.NAME + ";";
        final String dropIngredient = "DROP TABLE IF EXISTS  " + IngredientsTable.NAME + ";";
        final String dropStep = "DROP TABLE IF EXISTS  " + StepsTable.NAME + ";";

        sqLiteDatabase.execSQL(dropRecipe);
        sqLiteDatabase.execSQL(dropIngredient);
        sqLiteDatabase.execSQL(dropStep);

        onCreate(sqLiteDatabase);

        Log.d("DB_UPGRADE", "Upgrading db from version " + i + " to version " + i1);
    }
}
