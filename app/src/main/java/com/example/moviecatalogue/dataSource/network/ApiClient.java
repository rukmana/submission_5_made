package com.example.moviecatalogue.dataSource.network;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.MovieCatalogueApplication;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesResponseData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowResponseData;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {

    private static ApiInterface mApiInterface;

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w92";
    public static final String BASE_URL_POSTER_W500 = "https://image.tmdb.org/t/p/w185";

    public static ApiInterface getInterface() {

        OkHttpClient.Builder okHttpClientBuilder;
        okHttpClientBuilder = new OkHttpClient.Builder();

        // Add logging for debug builds
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        // Add Chuck interceptor
        okHttpClientBuilder.addInterceptor(new ChuckInterceptor(MovieCatalogueApplication.getContext()));

        // Set timeout duration
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        if (mApiInterface == null) {
            // Initialize the retrofit for API calls
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();

            mApiInterface = retrofit.create(ApiInterface.class);
        }

        return mApiInterface;
    }


    public interface ApiInterface {

        @GET("discover/movie")
        Call<MoviesResponseData> getMovie(@Query("api_key") String apiKey);

        @GET("discover/tv")
        Call<TvShowResponseData> getTvShow(@Query("api_key") String apiKey);

        @GET("search/movie")
        Call<MoviesResponseData> getSeachMovie(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("query") String query);

        @GET("search/tv")
        Call<TvShowResponseData> getSearchTvShow(@Query("api_key") String apiKey,
                                                 @Query("language") String language,
                                                 @Query("query") String query);

        @GET("discover/movie")
        Call<MoviesResponseData>getReleaseData(@Query("api_key") String apiKey,
                                               @Query("primary_release_date.gte") String date1,
                                               @Query("primary_release_date.lte") String date2);
    }
}
