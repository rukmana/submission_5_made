package com.example.moviecatalogue.dataSource.network.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesResponseData {

    @SerializedName("page")
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("results")
    ArrayList<MoviesData> results;

    @SerializedName("status_message")
    String statusMessage;


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


    public ArrayList<MoviesData> getResults() {
        return results;
    }

    public void setResults(ArrayList<MoviesData> results) {
        this.results = results;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}

