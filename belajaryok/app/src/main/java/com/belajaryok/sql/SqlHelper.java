package com.belajaryok.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "db_ayobelajar";
    public static final String DB_TABLE_USER = "tbl_user";
    public static final String TABLE_ID = "id";
    public static final String TABLE_USER_USERNAME = "username";
    public static final String TABLE_USER_POINT = "point";
    public static final String TABLE_LEVEL = "level";
    public static final String TABLE_HEART = "heart";

    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Buat tabel user
        db.execSQL("CREATE TABLE " + DB_TABLE_USER + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_USER_USERNAME + " TEXT NOT NULL," +
                TABLE_LEVEL + " INTEGER DEFAULT 1, " +
                TABLE_HEART + " INTEGER DEFAULT 5," +
                TABLE_USER_POINT + " INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USER);
        onCreate(db);
    }
    public boolean insertUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_USERNAME, username);
        values.put(TABLE_USER_POINT, 0);

        long result = db.insert(DB_TABLE_USER, null, values);
        db.close();
        return result != -1; // true jika berhasil, false jika gagal
    }

    public boolean updatePoint(String username, int pointToAdd) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Ambil point lama dari user berdasarkan username
        String query = "SELECT " + TABLE_USER_POINT + " FROM " + DB_TABLE_USER +
                " WHERE " + TABLE_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            int currentPoint = cursor.getInt(0);
            int newPoint = currentPoint + pointToAdd;

            ContentValues values = new ContentValues();
            values.put(TABLE_USER_POINT, newPoint);

            int rowsAffected = db.update(DB_TABLE_USER, values,
                    TABLE_USER_USERNAME + " = ?", new String[]{username});

            cursor.close();
            db.close();
            return rowsAffected > 0;
        }

        if (cursor != null) cursor.close();
        db.close();
        return false;
    }

    public int getPoint(String username) {
        int point = 0; // default value
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_USER_POINT + " FROM " + DB_TABLE_USER + " WHERE " + TABLE_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                point = cursor.getInt(0); // ambil data kolom pertama (point)
            }
            cursor.close();
        }

        db.close();
        return point;
    }


    public String getTableHeart(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_HEART + " FROM " + DB_TABLE_USER + " WHERE " + TABLE_USER_USERNAME + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});
        String heart = "";
        if (cursor.moveToFirst()) {
            heart = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return heart;
    }

    public String getTableLevel(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_LEVEL + " FROM " + DB_TABLE_USER + " WHERE " + TABLE_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        String level = "";
        if (cursor.moveToFirst()) {
            level = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return level;
    }

    public boolean setTableLevel(String username, int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_LEVEL, level);
        int rowsAffected = db.update(DB_TABLE_USER, values, TABLE_USER_USERNAME + " = ?", new String[]{username});
        db.close();
        return rowsAffected > 0;
    }

    public boolean setTableHeart(String username, int heart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_HEART, heart);
        int rowsAffected = db.update(DB_TABLE_USER, values, TABLE_USER_USERNAME + " = ?", new String[]{username});
        db.close();
        return rowsAffected > 0;
    }

}
