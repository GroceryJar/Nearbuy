package app.amaker.com.nearbuy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        final EditText itemWanted = (EditText) findViewById(R.id.searchItem);
        itemWanted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchItem(itemWanted.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void searchItem(String itemName) {

        Posts.PostsDbHelper mDbHelper = new Posts().new PostsDbHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database we will actually use
        // after the query.
        String[] projection = {
                Posts.PostEntry._ID,
                Posts.PostEntry.COLUMN_NAME_ITEM_NAME,
                Posts.PostEntry.COLUMN_NAME_FIRST_NAME,
                Posts.PostEntry.COLUMN_NAME_LAST_NAME,
                Posts.PostEntry.COLUMN_NAME_DESCRIPTION,
                Posts.PostEntry.COlUMN_NAME_IMAGE,
                Posts.PostEntry.COLUMN_NAME_CREATED_AT
            };

        String sortOrder = Posts.PostEntry.COLUMN_NAME_ITEM_NAME + " DESC";

        Cursor c = db.query(
                Posts.PostEntry.TABLE_NAME,
                projection,
                Posts.PostEntry.COLUMN_NAME_ITEM_NAME + " LIKE '%" + itemName + "%'",
                null,
                null,
                null,
                sortOrder
        );

        LinearLayout posts = (LinearLayout) findViewById(R.id.searchResults);

        c.moveToFirst();
        int counter = 0;
        while(c.moveToNext()) {
            posts.addView(findViewById(R.id.singlePost));
            LinearLayout singlePost = (LinearLayout) posts.getChildAt(counter);
            TextView item = (TextView) singlePost.getChildAt(0);
            TextView name = (TextView) singlePost.getChildAt(1);
            TextView date = (TextView) singlePost.getChildAt(2);
            item.setText(c.getString(c.getColumnIndexOrThrow(Posts.PostEntry.COLUMN_NAME_ITEM_NAME)));
            String fullName = c.getString(c.getColumnIndexOrThrow(Posts.PostEntry.COLUMN_NAME_FIRST_NAME)) + " "
            + c.getString(c.getColumnIndexOrThrow(Posts.PostEntry.COLUMN_NAME_LAST_NAME));
            name.setText(fullName);
            date.setText(c.getColumnIndexOrThrow(Posts.PostEntry.COLUMN_NAME_CREATED_AT));
            counter++;
        }
        c.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
