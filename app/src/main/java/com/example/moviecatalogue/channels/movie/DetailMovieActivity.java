package com.example.moviecatalogue.channels.movie;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.BaseActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.local.database.MovieCatalogueDatabase;
import com.example.moviecatalogue.dataSource.local.model.MoviesLocalData;
import com.example.moviecatalogue.dataSource.network.ApiClient;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;

import static com.example.moviecatalogue.MovieCatalogueApplication.db;

public class DetailMovieActivity extends BaseActivity implements View.OnClickListener {

    public static String EXTRA_MOVIES_DATA = "EXTRA_MOVIES_DATA";
    public static String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    ImageView ivFavoriteFalse, ivFavoriteTrue;

    MoviesLocalData moviesLocalData;
    MoviesData data;
    int dataId;
    String imageData, titleData, descData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final ImageView ivMovie = findViewById(R.id.iv_image_detail);
        TextView tvTitle = findViewById(R.id.tv_title_description);
        TextView tvDesc = findViewById(R.id.tv_detail_description);
        ivFavoriteFalse = findViewById(R.id.iv_favorite_false);
        ivFavoriteTrue = findViewById(R.id.iv_favorite_true);

        data = getIntent().getParcelableExtra(EXTRA_MOVIES_DATA);
        dataId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        if(dataId == 0) dataId = data.getId();

        db = Room.databaseBuilder(this,
                MovieCatalogueDatabase.class,"movies").allowMainThreadQueries().build();
        moviesLocalData = db.moviesDao().loadByDataId(dataId);
        if(moviesLocalData != null){
            isDataSavaLocal(true);
        } else isDataSavaLocal(false);

        if(data != null){
            imageData = ApiClient.BASE_URL_POSTER_W500 + data.getPosterPath();
            titleData = data.getTitle();
            descData = data.getOverview();
        } else {
            imageData = ApiClient.BASE_URL_POSTER_W500 + moviesLocalData.getPosterPath();
            titleData = moviesLocalData.getTitle();
            descData = moviesLocalData.getOverview();
        }

        Glide.with(this)
                .load(imageData)
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(ivMovie);

        tvTitle.setText(titleData);
        tvDesc.setText(descData);
        ivFavoriteFalse.setOnClickListener(this);
        ivFavoriteTrue.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_favorite_false:
                moviesLocalData = new MoviesLocalData();
                moviesLocalData.setDataId(data.getId());
                moviesLocalData.setTitle(data.getTitle());
                moviesLocalData.setOverview(data.getOverview());
                moviesLocalData.setPosterPath(data.getPosterPath());
                moviesLocalData.setFullPath(ApiClient.BASE_URL_POSTER_W500 + data.getPosterPath());

                db.moviesDao().insertAll(moviesLocalData);

                isDataSavaLocal(true);
                Toast.makeText(this, getResources().getText(R.string.succses_add_favorite), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_favorite_true:
                db.moviesDao().deleteByUserId(dataId);

                isDataSavaLocal(false);
                Toast.makeText(this, getResources().getText(R.string.succses_delete_favorite), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void isDataSavaLocal(Boolean state) {
        if (state) {
            ivFavoriteFalse.setVisibility(View.GONE);
            ivFavoriteTrue.setVisibility(View.VISIBLE);
        } else {
            ivFavoriteFalse.setVisibility(View.VISIBLE);
            ivFavoriteTrue.setVisibility(View.GONE);
        }
    }
}
