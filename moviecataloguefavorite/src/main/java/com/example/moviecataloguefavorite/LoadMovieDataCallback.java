package com.example.moviecataloguefavorite;

import android.database.Cursor;

public interface LoadMovieDataCallback {
    void postExecute(Cursor favorite);
}
