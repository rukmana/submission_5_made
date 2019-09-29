package com.example.moviecatalogue.dataSource.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MoviesLocalData {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "data_id")
    int dataId;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "poster_path")
    String posterPath;
    @ColumnInfo(name = "overview")
    String overview;
    @ColumnInfo(name = "full_path")
    String fullPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
