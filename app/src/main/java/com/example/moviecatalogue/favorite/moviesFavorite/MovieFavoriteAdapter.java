package com.example.moviecatalogue.favorite.moviesFavorite;

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
import com.example.moviecatalogue.dataSource.local.model.MoviesLocalData;
import com.example.moviecatalogue.dataSource.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MoviesLocalData> mData = new ArrayList<>();

    public void setData(List<MoviesLocalData> items, Context context) {
        this.context = context;
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movies_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MoviesLocalData data = mData.get(i);
        viewHolder.bind(data);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, data.getDataId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovies;
        TextView tvTitle, tvDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovies = itemView.findViewById(R.id.iv_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }

        void bind(MoviesLocalData data) {
            Glide.with(context)
                    .load(ApiClient.BASE_URL_POSTER + data.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(ivMovies);
            tvTitle.setText(data.getTitle());
            tvDesc.setText(data.getOverview());
        }
    }
}
