package com.example.moviecatalogue.channels.movie;

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
import com.example.moviecatalogue.dataSource.network.ApiClient;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesData> mData = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MoviesData> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final MoviesData item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MoviesData data = mData.get(i);
        viewHolder.bind(data);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIES_DATA, data);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
    }
}
