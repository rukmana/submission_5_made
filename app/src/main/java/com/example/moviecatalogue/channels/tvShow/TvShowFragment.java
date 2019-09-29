package com.example.moviecatalogue.channels.tvShow;

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
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;

import java.util.ArrayList;

public class TvShowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;
    private RecyclerView rvTvShow;
    SwipeRefreshLayout swipeLayout;
    public static final String PAGE_POSITION = "PAGE_POSITION";
    private int mPage;

    public TvShowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvShowViewModel = ViewModelProviders.of(getActivity())
                .get(TvShowViewModel.class);

        swipeLayout = view.findViewById(R.id.srl_tv_show);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        onRefresh();
        tvShowViewModel.getTvShowData().observe(this, getMovies);

        tvShowAdapter = new TvShowAdapter(getContext());
        tvShowAdapter.notifyDataSetChanged();

        rvTvShow = view.findViewById(R.id.rv_tv_show);
        showRecycleViewMovie();
    }


    private Observer<ArrayList<TvShowData>> getMovies = new Observer<ArrayList<TvShowData>>() {
        @Override
        public void onChanged(ArrayList<TvShowData> tvShowData) {
            if (tvShowData != null) {
                tvShowAdapter.setData(tvShowData);
                showLoading(false);
            }
        }
    };

    private void showRecycleViewMovie() {
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowAdapter = new TvShowAdapter(getContext());
        rvTvShow.setAdapter(tvShowAdapter);
    }

    @Override
    public void onRefresh() {
        showLoading(true);
        tvShowViewModel.getData(((BaseActivity)getActivity()).getApiKey());
    }

    private void showLoading(Boolean state) {
        if (state) {
            swipeLayout.setRefreshing(true);
        } else {
            swipeLayout.setRefreshing(false);
        }
    }

    public static TvShowFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_POSITION, page);
        TvShowFragment fragment = new TvShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
