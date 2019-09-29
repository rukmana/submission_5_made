package com.example.moviecatalogue.provider;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.moviecatalogue";
    private static final String SCHEME = "content";

    //
    private DatabaseContract(){}

    public static final class NoteColumns implements BaseColumns {
        public static final String TABLE_NAME = "movies";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
