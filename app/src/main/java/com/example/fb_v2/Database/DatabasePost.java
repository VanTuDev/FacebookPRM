package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabasePost extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "posts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_LIKE_COUNT = "like_count";
    public static final String COLUMN_COMMENT_COUNT = "comment_count";

    public DatabasePost(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT, " +
                COLUMN_LIKE_COUNT + " INTEGER DEFAULT 0, " +
                COLUMN_COMMENT_COUNT + " INTEGER DEFAULT 0" +
                ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPost(String userName, String content, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, userName);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_IMAGE_URI, imageUri);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;  // returns true if insert is successful
    }

    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
