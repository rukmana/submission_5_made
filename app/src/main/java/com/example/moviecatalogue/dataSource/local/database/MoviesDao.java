package com.example.moviecatalogue.dataSource.local.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.moviecatalogue.dataSource.local.model.MoviesLocalData;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movieslocaldata")
    List<MoviesLocalData> getAll();

    @Query("SELECT * FROM movieslocaldata WHERE data_id LIKE :nama ")
    MoviesLocalData loadByDataId(int nama);

    @Query("DELETE FROM movieslocaldata WHERE data_id = :userId")
    abstract void deleteByUserId(long userId);

    @Insert
    void insertAll(MoviesLocalData movies);

    @Query("SELECT full_path FROM movieslocaldata")
    List<String> selectMovieFavorite();

    @Query("SELECT * FROM movieslocaldata")
    Cursor getMovieFavorite();
}
