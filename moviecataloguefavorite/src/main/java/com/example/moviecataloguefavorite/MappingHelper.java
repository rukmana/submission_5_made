package com.example.moviecataloguefavorite;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.moviecataloguefavorite.DatabaseContract.NoteColumns.ID;
import static com.example.moviecataloguefavorite.DatabaseContract.NoteColumns.OVERVIEW;
import static com.example.moviecataloguefavorite.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.example.moviecataloguefavorite.DatabaseContract.NoteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<MovieData> mapCursorToArrayList(Cursor cursor) {
        ArrayList<MovieData> notesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));
            notesList.add(new MovieData(id, title, description, posterPath));
        }

        return notesList;
    }
}
