package com.example.moviecatalogue.channels.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalogue.dataSource.network.Utils;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesResponseData;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MoviesData>> listMovies = new MutableLiveData<>();

    public void getData(String apiKey){

        Call<MoviesResponseData> call = Utils.geClient().getMovie(apiKey);
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
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    LiveData<ArrayList<MoviesData>> getMoviesData() {
        return listMovies;
    }
}
