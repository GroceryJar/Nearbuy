package app.amaker.com.nearbuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class Posts {
    public Posts() {}

    /* Inner class that defines the table contents */
    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_USER_ID = "post id";
        public static final String COLUMN_NAME_FIRST_NAME = "first name";
        public static final String COLUMN_NAME_LAST_NAME = "last name";
        public static final String COLUMN_NAME_DESCRIPTION = "link";
        public static final String COlUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CREATED_AT = "created at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";

        public static final String TEXT_TYPE = " TEXT";
        public static final String REAL_TYPE = " REAL";
        public static final String BLOB_TYPE = " BLOB";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PostEntry.TABLE_NAME + " (" +
                        PostEntry._ID + " INTEGER PRIMARY KEY," +
                        PostEntry.COLUMN_NAME_USER_ID + TEXT_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        PostEntry.COlUMN_NAME_IMAGE + BLOB_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_CREATED_AT + INTEGER_TYPE + COMMA_SEP +
                        PostEntry.COLUMN_NAME_UPDATED_AT + INTEGER_TYPE + COMMA_SEP + " )";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PostEntry.TABLE_NAME;

    }
    public class PostsDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "users.db";

        public PostsDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PostEntry.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(PostEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}