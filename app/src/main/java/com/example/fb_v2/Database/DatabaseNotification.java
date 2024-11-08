package com.example.fb_v2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fb_v2.Model.Notification;

import java.util.ArrayList;
import java.util.List;

public class DatabaseNotification extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifications.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIME = "time";

    public DatabaseNotification(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_TIME + " TEXT" +
                ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    public boolean insertNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, notification.getUserName());
        values.put(COLUMN_CONTENT, notification.getContent());
        values.put(COLUMN_TIME, notification.getTime());

        long result = db.insert(TABLE_NOTIFICATIONS, null, values);
        return result != -1;
    }

    // Phương thức lấy tất cả thông báo từ database
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATIONS, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null) {
            try {
                int userNameIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_NAME);
                int contentIndex = cursor.getColumnIndexOrThrow(COLUMN_CONTENT);
                int timeIndex = cursor.getColumnIndexOrThrow(COLUMN_TIME);

                while (cursor.moveToNext()) {
                    String userName = cursor.getString(userNameIndex);
                    String content = cursor.getString(contentIndex);
                    String time = cursor.getString(timeIndex);

                    notifications.add(new Notification(userName, content, time));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return notifications;
    }

}