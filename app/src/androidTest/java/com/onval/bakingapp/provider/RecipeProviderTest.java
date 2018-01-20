package com.onval.bakingapp.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import com.onval.bakingapp.data.Recipe;

import static com.onval.bakingapp.provider.RecipeContract.RecipesTable;

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
        Recipe mockRecipe;

        //initialize mockrecipe;

        //test that stuff was inserted properly

    }

//    public void testQuery() throws Exception {
//        // get elements from database
//        Cursor cursor = getMockContentResolver().query(uri, null, null, null, null);
//
//        // assert if you've really inserted the elements
//        assertTrue("Query returned null", cursor != null);
//
//        // assert cursor isn't empty
//        assertTrue(cursor.moveToFirst());
//
//        //assert that the cursor contains only two elements
//        cursor.moveToPosition(1);
//        assertTrue(cursor.isLast());
//
//        // assert that the elements inserted are the right ones
//        cursor.moveToPosition(0);
//        assertEquals("Meat", cursor.getString(cursor.getColumnIndex(RecipesTable.NAME_COLUMN)));
//        assertEquals("", cursor.getString(cursor.getColumnIndex(RecipesTable.IMAGE_COLUMN)));
//        assertEquals(5, cursor.getInt(cursor.getColumnIndex(RecipesTable.NAME_COLUMN)));
//
//        //close the cursor
//        cursor.close();
//
//    }

//    public void testUpdate() throws Exception {
//
//    }
//
//    public void testDelete() throws Exception {
//    }
}