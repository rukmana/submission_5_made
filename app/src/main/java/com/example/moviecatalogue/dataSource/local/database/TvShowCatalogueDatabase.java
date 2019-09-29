package com.example.moviecatalogue.dataSource.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.moviecatalogue.dataSource.local.model.TvShowLocalData;

@Database(entities = {TvShowLocalData.class}, version = 1)
public abstract class TvShowCatalogueDatabase extends RoomDatabase {
    public abstract TvShowDao tvShowDao();
}

