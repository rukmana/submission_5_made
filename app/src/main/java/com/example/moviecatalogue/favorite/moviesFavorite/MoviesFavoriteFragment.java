package com.example.moviecatalogue.favorite.moviesFavorite;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.local.database.MovieCatalogueDatabase;
import com.example.moviecatalogue.dataSource.local.model.MoviesLocalData;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviecatalogue.MovieCatalogueApplication.db;

public class MoviesFavoriteFragment extends Fragment {
    private MovieFavoriteAdapter movieAdapter;
    private RecyclerView rvMovie;
    private TextView tvNoData;
    List<MoviesLocalData> moviesLocalData = new ArrayList<>();


    public static final String PAGE_POSITION = "PAGE_POSITION";
    private int mPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
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

        movieAdapter = new MovieFavoriteAdapter();
        movieAdapter.notifyDataSetChanged();

        tvNoData = view.findViewById(R.id.tv_no_data);
        rvMovie = view.findViewById(R.id.rv_movies);
        showRecycleViewMovie();
    }

    @Override
    public void onResume() {
        super.onResume();
        showRecycleViewMovie();
    }

    private Observer<List<MoviesLocalData>> getMovies = new Observer<List<MoviesLocalData>>() {
        @Override
        public void onChanged(List<MoviesLocalData> moviesData) {
            if (moviesData != null) {
                movieAdapter.setData(moviesData, getActivity());
            }
        }
    };

    private void showRecycleViewMovie() {
        db = Room.databaseBuilder(getActivity(),
                MovieCatalogueDatabase.class, "movies").allowMainThreadQueries().build();
        moviesLocalData = db.moviesDao().getAll();

        if(!moviesLocalData.isEmpty()){
            rvMovie.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
        } else {
            rvMovie.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieFavoriteAdapter();
        movieAdapter.setData(moviesLocalData, getContext());
        rvMovie.setAdapter(movieAdapter);
    }

    public static MoviesFavoriteFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_POSITION, page);
        MoviesFavoriteFragment fragment = new MoviesFavoriteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
