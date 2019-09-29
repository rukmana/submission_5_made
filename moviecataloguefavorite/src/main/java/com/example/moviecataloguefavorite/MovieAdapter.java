package com.example.moviecataloguefavorite;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final ArrayList<MovieData> list = new ArrayList<>();
    private final Activity activity;

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieData> getList() {
        return list;
    }

    public void setListNotes(ArrayList<MovieData> listNotes) {
        this.list.clear();
        this.list.addAll(listNotes);
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(getList().get(i).getTitle());
        viewHolder.tvDesc.setText(getList().get(i).getOverview());
        Glide.with(activity)
                    .load("https://image.tmdb.org/t/p/w92" + getList().get(i).getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(viewHolder.ivMovies);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "List Data ke " + getList().get(i).id, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovies;
        TextView tvTitle, tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ivMovies = itemView.findViewById(R.id.iv_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }
    }
}