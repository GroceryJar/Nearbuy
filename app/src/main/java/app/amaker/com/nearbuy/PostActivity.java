package app.amaker.com.nearbuy;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;

import java.util.Date;

public class PostActivity extends ActionBarActivity {
    EditText item_name;
    EditText item_description;
    EditText item_price;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        item_name = (EditText) findViewById(R.id.item_name);
        item_description = (EditText) findViewById(R.id.item_description);
        item_price = (EditText) findViewById(R.id.item_price);
        submit_button = (Button) findViewById(R.id.button_post);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();

            }
        });
    }

    private void addPost() {
        Profile p = Profile.getCurrentProfile();

        String itna = item_name.getText().toString();
        String itdes = item_description.getText().toString();
        Double itpr = Double.parseDouble(item_price.getText().toString());
        String createTime = new Date().toString();
        String updateTime = new Date().toString();

        Posts.PostsDbHelper pDbHelper = new Posts().new PostsDbHelper(getApplicationContext());
        SQLiteDatabase db = pDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Posts.PostEntry.COLUMN_NAME_FIRST_NAME, p.getFirstName());
        values.put(Posts.PostEntry.COLUMN_NAME_LAST_NAME, p.getLastName());
        values.put(Posts.PostEntry.COLUMN_NAME_ITEM_NAME, itna);
        values.put(Posts.PostEntry.COLUMN_NAME_DESCRIPTION, itdes);
        values.put(Posts.PostEntry.COLUMN_NAME_PRICE, itpr);

        long newRowId;
        newRowId = db.insert(Posts.PostEntry.TABLE_NAME, null, values);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
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
