package com.example.moviecatalogue.channels.tvShow;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.moviecatalogue.dataSource.network.Utils;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowResponseData;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShowData>> listTvShowData = new MutableLiveData<>();

    public void getData(String apiKey){

        Call<TvShowResponseData> call = Utils.geClient().getTvShow(apiKey);
        call.enqueue(new Callback<TvShowResponseData>() {
            @Override
            public void onResponse(Call<TvShowResponseData> call, Response<TvShowResponseData> response) {
                TvShowResponseData responseData = response.body();
                if(response.isSuccessful()){
                    try {
                        listTvShowData.postValue(responseData.getResults());
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

    LiveData<ArrayList<TvShowData>> getTvShowData() {
        return listTvShowData;
    }
}
