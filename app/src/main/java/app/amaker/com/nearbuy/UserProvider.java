import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.LiveFolders;
import android.text.TextUtils;
import android.util.Log;

import java.lang.Override;
import java.util.HashMap;

public class UserProvider extends ContentProvider {
    private static final String TAG = "UserProvider";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2;
    private static final String USERS_TABLE_NAME = "users";

    private static HashMap<String, String> usersMap;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " ("
                    + NoteColumns._ID + " INTEGER PRIMARY KEY,"
                    + NoteColumns.TITLE + " TEXT,"
                    + NoteColumns.NOTE + " TEXT,"
                    + NoteColumns.CREATED_DATE + " INTEGER,"
                    + NoteColumns.MODIFIED_DATE + " INTEGER"
                    + ");");
        }
    }
}