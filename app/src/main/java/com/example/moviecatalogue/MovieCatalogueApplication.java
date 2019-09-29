package com.example.moviecatalogue;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.moviecatalogue.dataSource.local.database.MovieCatalogueDatabase;
import com.example.moviecatalogue.dataSource.local.database.TvShowCatalogueDatabase;

public class MovieCatalogueApplication extends Application {

    @SuppressWarnings("NullableProblems")
    @SuppressLint("StaticFieldLeak")
    @NonNull
    private static Context mContext;
    public static MovieCatalogueDatabase db;
    public static TvShowCatalogueDatabase dbTv;

    @NonNull
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        db = Room.databaseBuilder(getApplicationContext(),
                MovieCatalogueDatabase.class,"movies").allowMainThreadQueries().build();

        dbTv = Room.databaseBuilder(getApplicationContext(),
                TvShowCatalogueDatabase.class, "tvshow").allowMainThreadQueries().build();

    }
}
