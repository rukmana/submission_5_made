package com.example.moviecatalogue.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.channels.movie.DetailMovieActivity;
import com.example.moviecatalogue.dataSource.network.ApiClient;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesData> mDataMovie = new ArrayList<>();
    private ArrayList<TvShowData> mDataTv = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setDataMovie(ArrayList<MoviesData> items) {
        mDataMovie.clear();
        mDataMovie.addAll(items);
        notifyDataSetChanged();
    }

    public void setDataTv(ArrayList<TvShowData> items) {
        mDataTv.clear();
        mDataTv.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_item, viewGroup, false);
        return new SearchAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int i) {
        if(mDataMovie.size() != 0) {
            final MoviesData data = mDataMovie.get(i);
            viewHolder.bind(data);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIES_DATA, data);
                    context.startActivity(intent);
                }
            });
        } else if(mDataTv.size() != 0){
            final TvShowData data = mDataTv.get(i);
            viewHolder.bindTv(data);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIES_DATA, data);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int data = 0;
        if(mDataMovie.size() != 0){
            data = mDataMovie.size();
        } else {
            data = mDataTv.size();
        }
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovies;
        TextView tvTitle, tvDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovies = itemView.findViewById(R.id.iv_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }

        void bind(MoviesData data){
            Glide.with(context)
                    .load(ApiClient.BASE_URL_POSTER + data.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(ivMovies);
            tvTitle.setText(data.getTitle());
            tvDesc.setText(data.getOverview());
        }

        void bindTv(TvShowData data){
            Glide.with(context)
                    .load(ApiClient.BASE_URL_POSTER + data.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(ivMovies);
            tvTitle.setText(data.getName());
            tvDesc.setText(data.getOverview());
        }

    }
}
