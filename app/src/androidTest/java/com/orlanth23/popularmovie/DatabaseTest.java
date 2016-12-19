package com.orlanth23.popularmovie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.orlanth23.popularmovie.data.MovieContract;
import com.orlanth23.popularmovie.data.MovieDbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Before
    public void deleteDb(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    @Test
    public void testOpenDatabase() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDatabase db = new MovieDbHelper(appContext).getWritableDatabase();
        assertEquals(true, db.isOpen());


        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        boolean tableFound = false;

        while( c.moveToNext() ){
           if (c.getString(0).equals(MovieContract.MovieEntry.TABLE_NAME)){
               tableFound = true;
           }
        }

        assertTrue(tableFound);

        c.close();
    }
}
