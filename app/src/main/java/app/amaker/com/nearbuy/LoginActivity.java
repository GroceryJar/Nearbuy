package app.amaker.com.nearbuy;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;

public class LoginActivity extends ActionBarActivity {
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateDatabase();
                // jump to another activity
            }

            @Override
            public void onCancel() {
                showAlert();
            }

            @Override
            public void onError(FacebookException exception) {
                showAlert();
            }

            private void showAlert() {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.login_failed)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void updateDatabase() {
        boolean enableAccess = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();

        if(enableAccess && profile != null) {
            Users.UsersDbHelper mDbHelper = new Users().new UsersDbHelper(getApplicationContext());
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Users.UserEntry.COLUMN_NAME_USER_ID, profile.getId());
            values.put(Users.UserEntry.COLUMN_NAME_FIRST_NAME, profile.getFirstName());
            values.put(Users.UserEntry.COLUMN_NAME_LAST_NAME, profile.getLastName());
            values.put(Users.UserEntry.COLUMN_NAME_LINKURI, profile.getLinkUri().toString());

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(Users.UserEntry.TABLE_NAME, null, values);
        }
    }
}
