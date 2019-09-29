package com.example.moviecatalogue.dataSource.network.model.tvShow;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShowData implements Parcelable {

    @SerializedName("original_name")
    String originalName;

    @SerializedName("id")
    int id;

    @SerializedName("vote_average")
    double voteAverage;

    @SerializedName("name")
    String name;

    @SerializedName("popularity")
    double popularity;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("original_language")
    String originalLanguage;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("overview")
    String overview;

    @SerializedName("first_air_data")
    String firstAirData;

    protected TvShowData(Parcel in) {
        originalName = in.readString();
        id = in.readInt();
        voteAverage = in.readDouble();
        name = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        firstAirData = in.readString();
    }

    public static final Creator<TvShowData> CREATOR = new Creator<TvShowData>() {
        @Override
        public TvShowData createFromParcel(Parcel in) {
            return new TvShowData(in);
        }

        @Override
        public TvShowData[] newArray(int size) {
            return new TvShowData[size];
        }
    };

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirData() {
        return firstAirData;
    }

    public void setFirstAirData(String firstAirData) {
        this.firstAirData = firstAirData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalName);
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(name);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(firstAirData);
    }
}
