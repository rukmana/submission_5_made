package com.example.moviecatalogue.dataSource.network.model.tvShow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponseData {

    @SerializedName("page")
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("results")
    ArrayList<TvShowData> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<TvShowData> getResults() {
        return results;
    }

    public void setResults(ArrayList<TvShowData> results) {
        this.results = results;
    }
}
