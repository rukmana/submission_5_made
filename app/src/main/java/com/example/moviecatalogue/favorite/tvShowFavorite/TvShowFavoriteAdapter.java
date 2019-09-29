package com.example.moviecatalogue.favorite.tvShowFavorite;

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
import com.example.moviecatalogue.channels.tvShow.DetailTvShowActivity;
import com.example.moviecatalogue.dataSource.local.model.TvShowLocalData;
import com.example.moviecatalogue.dataSource.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvShowLocalData> mData = new ArrayList<>();

    public void setData(List<TvShowLocalData> items, Context context) {
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
        final TvShowLocalData data = mData.get(i);
        viewHolder.bind(data);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_ID, data.getDataId());
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

        void bind(TvShowLocalData data) {
            Glide.with(context)
                    .load(ApiClient.BASE_URL_POSTER + data.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(ivMovies);
            tvTitle.setText(data.getTitle());
            tvDesc.setText(data.getOverview());
        }
    }
}
