package app.amaker.com.nearbuy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ShowActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Posts.PostsDbHelper mDbHelper = new Posts().new PostsDbHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database we will actually use
        // after the query.
        String[] projection = {
                Posts.PostEntry._ID,
            };

        String sortOrder = Posts.PostEntry.COLUMN_NAME_CREATED_AT + " DESC";

        Cursor c = db.query(
                Posts.PostEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        c.moveToFirst();
        while(c.moveToNext()) {
            long itemId = c.getLong(
                    c.getColumnIndexOrThrow(Posts.PostEntry._ID)
            );
        }
        setContentView(R.layout.activity_show);
    }

}
