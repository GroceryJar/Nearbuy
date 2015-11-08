package app.amaker.com.nearbuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class Users {
    public Users() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_USER_ID = "user id";
        public static final String COLUMN_NAME_FIRST_NAME = "first name";
        public static final String COLUMN_NAME_LAST_NAME = "last name";
        public static final String COLUMN_NAME_LINKURI = "link";

        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                        UserEntry._ID + " INTEGER PRIMARY KEY," +
                        UserEntry.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_LINKURI + TEXT_TYPE  + " )";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    }
    public class UsersDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "users.db";

        public UsersDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(UserEntry.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(UserEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}