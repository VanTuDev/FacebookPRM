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

    public DatabasePost(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POST_TABLE = "CREATE TABLE posts (...)";
        db.execSQL(CREATE_POST_TABLE);

        // Tạo bảng likes
        String CREATE_LIKE_TABLE = "CREATE TABLE likes (" +
                "postId INTEGER, " +
                "userId INTEGER, " +
                "isLiked INTEGER, " +
                "PRIMARY KEY (postId, userId))";
        db.execSQL(CREATE_LIKE_TABLE);
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
