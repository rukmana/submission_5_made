package com.example.moviecataloguefavorite;

import com.google.gson.annotations.SerializedName;

public class MovieData {
    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("overview")
    String overview;

    public MovieData(String id, String title,String overview, String poster){
        this.id = id;
        this.title = title;
        this.posterPath = poster;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

