package com.example.moviecatalogue.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalogue.dataSource.network.Utils;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesResponseData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MoviesData>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShowData>> listTvShow = new MutableLiveData<>();

    public void getData(String apiKey, String type, String query){
        if(type.equalsIgnoreCase("movie")){
            Call<MoviesResponseData> call = Utils.geClient().getSeachMovie(apiKey, "en-US", query);
            call.enqueue(new Callback<MoviesResponseData>() {
                @Override
                public void onResponse(Call<MoviesResponseData> call, Response<MoviesResponseData> response) {
                    if(response.isSuccessful()){
                        MoviesResponseData responseData = response.body();
                        try {
                            listMovies.postValue(responseData.getResults());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponseData> call, Throwable t) {
                    Log.e("failure", "failure");
                }
            });
        } else {
            Call<TvShowResponseData> call = Utils.geClient().getSearchTvShow(apiKey, "en-US", query);
            call.enqueue(new Callback<TvShowResponseData>() {
                @Override
                public void onResponse(Call<TvShowResponseData> call, Response<TvShowResponseData> response) {
                    if(response.isSuccessful()){
                        TvShowResponseData responseData = response.body();
                        try {
                            listTvShow.postValue(responseData.getResults());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TvShowResponseData> call, Throwable t) {
                    Log.e("failure", "failure");
                }
            });
        }

    }

    LiveData<ArrayList<MoviesData>> getMoviesData() {
        return listMovies;
    }

    LiveData<ArrayList<TvShowData>> getTvShowData() {
        return listTvShow;
    }
}
