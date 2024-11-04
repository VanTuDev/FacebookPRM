package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fb_v2.Model.User;

public class DatabaseUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fb_v2.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PROFILE_IMAGE + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean addUser(User user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PROFILE_IMAGE, user.getProfileImage());

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID},
                COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{name, password}, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
}
