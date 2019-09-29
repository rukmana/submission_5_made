package com.example.moviecatalogue.dataSource.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.moviecatalogue.dataSource.local.model.MoviesLocalData;

@Database(entities = {MoviesLocalData.class}, version = 1)
public abstract class MovieCatalogueDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
