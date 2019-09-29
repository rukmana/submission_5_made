package com.example.moviecatalogue.provider;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moviecatalogue.dataSource.local.database.MovieCatalogueDatabase;

import static com.example.moviecatalogue.provider.DatabaseContract.AUTHORITY;
import static com.example.moviecatalogue.provider.DatabaseContract.NoteColumns.TABLE_NAME;

@SuppressLint("Registered")
public class MovieFavorieProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    MovieCatalogueDatabase db;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                db = Room.databaseBuilder(getContext(), MovieCatalogueDatabase.class, "movies").build();
                cursor = db.moviesDao().getMovieFavorite();
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
