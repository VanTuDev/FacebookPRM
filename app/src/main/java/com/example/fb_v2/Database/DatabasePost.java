package com.example.fb_v2.Database;

import android.annotation.SuppressLint;
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
    public static final String COLUMN_IS_LIKED = "is_liked";
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
                COLUMN_COMMENT_COUNT + " INTEGER DEFAULT 0, " +
                COLUMN_IS_LIKED + " INTEGER DEFAULT 0" + // New column for like status
                ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) { // Assuming new version with isLiked is version 2
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_IS_LIKED + " INTEGER DEFAULT 0");
        }
    }

    public boolean toggleLike(int postId, boolean isLiked, int currentLikeCount) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_LIKED, isLiked ? 1 : 0);  // Lưu trạng thái Like
            values.put(COLUMN_LIKE_COUNT, currentLikeCount);  // Lưu số lượng Like hiện tại

            int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(postId)});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
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
        return result > 0;  // Returns true if deletion was successful
    }

    public List<Post> getUserPosts(String userName) {
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "user_name = ?", new String[]{userName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getColumnIndex("_id") != -1 ? cursor.getInt(cursor.getColumnIndex("_id")) : 0;
                @SuppressLint("Range") String content = cursor.getColumnIndex("content") != -1 ? cursor.getString(cursor.getColumnIndex("content")) : "";
                @SuppressLint("Range") String imageUri = cursor.getColumnIndex("image_uri") != -1 ? cursor.getString(cursor.getColumnIndex("image_uri")) : null;
                @SuppressLint("Range") int likeCount = cursor.getColumnIndex("like_count") != -1 ? cursor.getInt(cursor.getColumnIndex("like_count")) : 0;
                @SuppressLint("Range") int commentCount = cursor.getColumnIndex("comment_count") != -1 ? cursor.getInt(cursor.getColumnIndex("comment_count")) : 0;
                @SuppressLint("Range") boolean isLiked = cursor.getColumnIndex("is_liked") != -1 && cursor.getInt(cursor.getColumnIndex("is_liked")) == 1;

                posts.add(new Post(id, userName, content, imageUri, likeCount, commentCount, isLiked));

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return posts;
    }


}

