package com.example.moviecatalogue.favorite.tvShowFavorite;

import android.annotation.SuppressLint;
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
import com.example.moviecatalogue.dataSource.local.database.TvShowCatalogueDatabase;
import com.example.moviecatalogue.dataSource.local.model.TvShowLocalData;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviecatalogue.MovieCatalogueApplication.dbTv;

public class TvShowFavoriteFragment extends Fragment {
    private TvShowFavoriteAdapter movieAdapter;
    private RecyclerView rvMovie;
    private TextView tvNoData;
    List<TvShowLocalData> tvShowLocalData = new ArrayList<>();


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

        movieAdapter = new TvShowFavoriteAdapter();
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

    private void showRecycleViewMovie() {
        dbTv = Room.databaseBuilder(getActivity(),
                TvShowCatalogueDatabase.class,"tvshow").allowMainThreadQueries().build();
        tvShowLocalData = dbTv.tvShowDao().getAll();

        if(!tvShowLocalData.isEmpty()){
            rvMovie.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
        } else {
            rvMovie.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new TvShowFavoriteAdapter();
        movieAdapter.setData(tvShowLocalData, getContext());
        rvMovie.setAdapter(movieAdapter);
    }

    public static TvShowFavoriteFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_POSITION, page);
        TvShowFavoriteFragment fragment = new TvShowFavoriteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

