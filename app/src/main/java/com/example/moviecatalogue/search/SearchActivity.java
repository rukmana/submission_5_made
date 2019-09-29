package com.example.moviecatalogue.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.moviecatalogue.BaseActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.channels.movie.MovieAdapter;
import com.example.moviecatalogue.channels.movie.MovieViewModel;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {

    private SearchAdapter movieAdapter;
    private SearchViewModel movieViewModel;
    private RecyclerView rvMovie;
    public static String EXTRA_TYPE = "EXTRA_TYPE";
    public static String EXTRA_QUERY = "EXTRA_QUERY";
    String type, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        movieViewModel = ViewModelProviders.of(this)
                .get(SearchViewModel.class);

        type = getIntent().getStringExtra(EXTRA_TYPE);
        query = getIntent().getStringExtra(EXTRA_QUERY);

        if(type.equalsIgnoreCase("movie")){
            movieViewModel.getData(getApiKey(), type, query);
            movieViewModel.getMoviesData().observe(this, getMovies);
        } else {
            movieViewModel.getData(getApiKey(), type, query);
            movieViewModel.getTvShowData().observe(this, getTvShow);
        }

        movieAdapter = new SearchAdapter(this);
        movieAdapter.notifyDataSetChanged();

        rvMovie = findViewById(R.id.rv_movies);
        showRecycleViewMovie();
    }

    private Observer<ArrayList<MoviesData>> getMovies = new Observer<ArrayList<MoviesData>>() {
        @Override
        public void onChanged(ArrayList<MoviesData> moviesData) {
            if (moviesData != null) {
                movieAdapter.setDataMovie(moviesData);
            }
        }
    };

    private Observer<ArrayList<TvShowData>> getTvShow = new Observer<ArrayList<TvShowData>>() {
        @Override
        public void onChanged(ArrayList<TvShowData> tvShowData) {
            if (tvShowData != null) {
                movieAdapter.setDataTv(tvShowData);
            }
        }
    };

    private void showRecycleViewMovie() {
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new SearchAdapter(this);
        rvMovie.setAdapter(movieAdapter);
    }

}
