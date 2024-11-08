package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fb_v2.Model.User;
import com.example.fb_v2.R;

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
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_PROFILE_IMAGE + " TEXT" + ")"; // Change to TEXT for storing URI as a string
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Method to add a user to the database
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

    // Method to check if a user with the given name and password exists
    public boolean checkUser(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID},
                COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{name, password}, null, null, null);

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }

    // Method to get the profile image of a user by name
    // DatabaseUser.java
    public String getUserProfileImage(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_PROFILE_IMAGE}, COLUMN_NAME + " = ?", new String[]{userName}, null, null, null);

        String profileImageUri = null; // Default to null
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_PROFILE_IMAGE);
            if (columnIndex != -1) {
                profileImageUri = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();

        // Nếu profileImageUri vẫn là null, trả về URI mặc định cho ảnh đại diện
        return profileImageUri != null ? profileImageUri : String.valueOf(R.drawable.avata);
    }


    public boolean updateUserProfile(String userName, String newUserName, String newProfileImageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newUserName);
        values.put(COLUMN_PROFILE_IMAGE, newProfileImageUri);

        int rowsAffected = db.update(TABLE_USER, values, COLUMN_NAME + " = ?", new String[]{userName});
        db.close();
        return rowsAffected > 0;
    }

    public boolean updateUserName(String oldUserName, String newUserName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newUserName);

        int rowsAffected = db.update(TABLE_USER, values, COLUMN_NAME + " = ?", new String[]{oldUserName});
        db.close();
        return rowsAffected > 0;
    }

    public boolean isUserNameExists(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_NAME}, COLUMN_NAME + " = ?", new String[]{userName}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
}

