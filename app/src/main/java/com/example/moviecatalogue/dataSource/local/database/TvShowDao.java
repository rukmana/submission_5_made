package com.example.moviecatalogue.dataSource.local.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.moviecatalogue.dataSource.local.model.TvShowLocalData;

import java.util.List;


@Dao
public interface TvShowDao {

    @Query("SELECT * FROM tvshowlocaldata")
    List<TvShowLocalData> getAll();

    @Query("SELECT * FROM tvshowlocaldata WHERE data_id LIKE :nama ")
    TvShowLocalData loadByDataId(int nama);

    @Query("DELETE FROM tvshowlocaldata WHERE data_id = :userId")
    abstract void deleteByUserId(long userId);

    @Insert
    void insertAll(TvShowLocalData tvShowLocalData);

    @Query("SELECT * FROM tvShowLocalData")
    Cursor getTvShowFavorite();
}

