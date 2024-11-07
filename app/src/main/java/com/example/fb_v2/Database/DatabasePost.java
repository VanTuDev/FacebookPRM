package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fb_v2.Model.Post;

import java.util.ArrayList;
import java.util.List;

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
        db.close();
        return result != -1;  // returns true if insert is successful
    }

    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public boolean deletePost(int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(postId)});
        db.close();
        return result > 0;
    }

    public List<Post> getUserPosts(String userName) {
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "user_name = ?", new String[]{userName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Sử dụng "_id" thay vì "id"
                int id = cursor.getColumnIndex("_id") != -1 ? cursor.getInt(cursor.getColumnIndex("_id")) : 0;
                String content = cursor.getColumnIndex("content") != -1 ? cursor.getString(cursor.getColumnIndex("content")) : "";
                String imageUri = cursor.getColumnIndex("image_uri") != -1 ? cursor.getString(cursor.getColumnIndex("image_uri")) : null;
                int likeCount = cursor.getColumnIndex("like_count") != -1 ? cursor.getInt(cursor.getColumnIndex("like_count")) : 0;
                int commentCount = cursor.getColumnIndex("comment_count") != -1 ? cursor.getInt(cursor.getColumnIndex("comment_count")) : 0;

                posts.add(new Post(id, userName, content, imageUri, likeCount, commentCount));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return posts;
    }

    // DatabasePost.java
    public boolean updateUserNameInPosts(String oldUserName, String newUserName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", newUserName);
        int rowsAffected = db.update(TABLE_NAME, values, "user_name = ?", new String[]{oldUserName});
        db.close();
        return rowsAffected > 0;
    }


}
