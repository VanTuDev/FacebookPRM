package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fb_v2.Model.Comment;

import java.util.ArrayList;
import java.util.List;

public class DatabaseComment extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "comments.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "comments";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseComment(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_POST_ID + " INTEGER, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_TIMESTAMP + " TEXT" + ")";
        db.execSQL(CREATE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POST_ID, comment.getPostId());
        values.put(COLUMN_USER_NAME, comment.getUserName());
        values.put(COLUMN_CONTENT, comment.getContent());
        values.put(COLUMN_TIMESTAMP, comment.getTimestamp());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_POST_ID + " = ?", new String[]{String.valueOf(postId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int userNameIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
                int contentIndex = cursor.getColumnIndex(COLUMN_CONTENT);
                int timestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);

                // Ensure indices are valid (non-negative)
                int id = idIndex != -1 ? cursor.getInt(idIndex) : 0; // Default to 0 if column is missing
                String userName = userNameIndex != -1 ? cursor.getString(userNameIndex) : "Unknown";
                String content = contentIndex != -1 ? cursor.getString(contentIndex) : "";
                String timestamp = timestampIndex != -1 ? cursor.getString(timestampIndex) : "";

                comments.add(new Comment(id, postId, userName, content, timestamp));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return comments;
    }

    public boolean deleteComment(int commentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(commentId)});
        db.close();
        return result > 0;
    }

}
