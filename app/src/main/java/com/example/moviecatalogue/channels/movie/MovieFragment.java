package com.example.moviecatalogue.channels.movie;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.BaseActivity;
import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private RecyclerView rvMovie;
    SwipeRefreshLayout swipeLayout;
    public static final String PAGE_POSITION = "PAGE_POSITION";
    private int mPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE_POSITION);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieViewModel = ViewModelProviders.of(getActivity())
                .get(MovieViewModel.class);

        swipeLayout = view.findViewById(R.id.srl_movie);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        onRefresh();
        movieViewModel.getMoviesData().observe(this, getMovies);

        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        rvMovie = view.findViewById(R.id.rv_movies);
        showRecycleViewMovie();
    }

    private Observer<ArrayList<MoviesData>> getMovies = new Observer<ArrayList<MoviesData>>() {
        @Override
        public void onChanged(ArrayList<MoviesData> moviesData) {
            if (moviesData != null) {
                movieAdapter.setData(moviesData);
                showLoading(false);
            }
        }
    };

    private void showRecycleViewMovie() {
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getContext());
        rvMovie.setAdapter(movieAdapter);
    }

    @Override
    public void onRefresh() {
        showLoading(true);
        movieViewModel.getData(((BaseActivity)getActivity()).getApiKey());
    }

    private void showLoading(Boolean state) {
        if (state) {
            swipeLayout.setRefreshing(true);
        } else {
            swipeLayout.setRefreshing(false);
        }
    }

    public static MovieFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_POSITION, page);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
