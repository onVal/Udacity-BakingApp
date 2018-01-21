package com.onval.bakingapp.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import com.onval.bakingapp.TestUtils.TestUtilities;
import com.onval.bakingapp.data.Recipe;

import static com.onval.bakingapp.provider.RecipeContract.IngredientsTable;
import static com.onval.bakingapp.provider.RecipeContract.RecipesTable;
import static com.onval.bakingapp.provider.RecipeContract.StepsTable;



/**
 * Created by gval on 08/01/2018.
 */
//@RunWith(AndroidJUnit4.class)
public class RecipeProviderTest extends ProviderTestCase2<RecipeProvider> {

    public RecipeProviderTest() {
        super(RecipeProvider.class, RecipeContract.AUTHORITY);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setContext(getMockContext());
    }

    public void testQuidMethods() throws Exception {
        // create uri to insert / query
        Uri uri = RecipeContract.RecipesTable.RECIPE_URI;

        //test uri validity
        assertEquals(uri.getPath(), "/recipe");

        //--INSERT--
        insertMockData(uri);

        //todo:TEST QUERY
        // -- QUERY -- get elements from database
        Cursor c = getMockContentResolver().query(uri, null,
                null, null, null);

        // assert that the cursor is not null
        assertTrue("Query returned null", c != null);

        // assert cursor isn't empty
        assertTrue("Cursor shouldn't be empty!", c.moveToFirst());

        //assert that the cursor contains only two elements
        c.moveToPosition(1);
        assertTrue(c.isLast());

        // assert that the elements inserted are the right ones
        c.moveToPosition(0);
        assertEquals("Meat", c.getString(c.getColumnIndex(RecipesTable.NAME_COLUMN)));
        assertEquals("", c.getString(c.getColumnIndex(RecipesTable.IMAGE_COLUMN)));
        assertEquals(5, c.getInt(c.getColumnIndex(RecipesTable.SERVINGS_COLUMN)));

        //close the cursor
        c.close();

        //todo:TEST DELETE

        //todo:TEST UPDATE


    }

    private void insertMockData(Uri uri) throws Exception {
        // create mock values to insert
        ContentValues recipe1 = new ContentValues();
        recipe1.put(RecipesTable._ID, 1);
        recipe1.put(RecipesTable.NAME_COLUMN, "Meat");
        recipe1.put(RecipesTable.IMAGE_COLUMN, "");
        recipe1.put(RecipesTable.SERVINGS_COLUMN, 5);

        ContentValues recipe2 = new ContentValues();
        recipe2.put(RecipesTable._ID, 2);
        recipe2.put(RecipesTable.NAME_COLUMN, "Fruit");
        recipe2.put(RecipesTable.IMAGE_COLUMN, "");
        recipe2.put(RecipesTable.SERVINGS_COLUMN, 12);

        // call content resolver and insert elements in the provider
        getMockContentResolver().insert(uri, recipe1);
        getMockContentResolver().insert(uri, recipe2);
    }

    public void testInsertRecipe() {
        Recipe mockRecipe = TestUtilities.mockRecipe();

        //call method to test
        RecipeProvider.insertRecipe(getMockContentResolver(), mockRecipe);

        //test that stuff was inserted properly
        //query the three tables and assert that data is valid
        Cursor c = getMockContentResolver().query(RecipesTable.RECIPE_URI, null,
                null, null, null);

        //test recipe table
        assertTrue("Cursor shouldn't be null", c != null);
        assertTrue("Cursor shouldn't be empty", c.moveToFirst());
        assertEquals("There should be only one recipe", 1, c.getCount());
        assertEquals("Incorrect recipe name",
                "Pancake", c.getString(c.getColumnIndex(RecipesTable.NAME_COLUMN)));

        c.close();

        //test ingredient
        c = getMockContentResolver().query(IngredientsTable.INGREDIENTS_URI, null,
                null, null, null);

        //test ingredient table
        assertTrue("Cursor shouldn't be null", c != null);
        assertTrue("Cursor shouldn't be empty", c.moveToFirst());
        assertEquals("There should be three ingredients", 3, c.getCount());

        assertEquals("Incorrect recipe name",
                "Yog", c.getString(c.getColumnIndex(IngredientsTable.INGREDIENT_COLUMN)));

        c.moveToNext();
        assertEquals("Incorrect recipe name",
                "Oat", c.getString(c.getColumnIndex(IngredientsTable.INGREDIENT_COLUMN)));

        c.moveToNext();
        assertEquals("Incorrect recipe name",
                1, c.getInt(c.getColumnIndex(IngredientsTable.QUANTITY_COLUMN)));

        assertFalse("There is no other element", c.moveToNext());

        c.close();

        //test steps
        c = getMockContentResolver().query(StepsTable.STEPS_URI, null,
                null, null, null);

        //test steps table
        assertTrue("Cursor shouldn't be null", c != null);
        assertTrue("Cursor shouldn't be empty", c.moveToFirst());
        assertEquals("There should be four ingredients", 4, c.getCount());

        c.moveToPosition(3);
        assertEquals("Incorrect recipe name",
                "Three", c.getString(c.getColumnIndex(StepsTable.SHORT_DESC_COLUMN)));

        assertFalse("There is no other element", c.moveToNext());

        c.close();
    }
}