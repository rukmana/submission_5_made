package com.example.moviecatalogue.channels.tvShow;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.local.database.TvShowCatalogueDatabase;
import com.example.moviecatalogue.dataSource.local.model.TvShowLocalData;
import com.example.moviecatalogue.dataSource.network.ApiClient;
import com.example.moviecatalogue.dataSource.network.model.tvShow.TvShowData;

import static com.example.moviecatalogue.MovieCatalogueApplication.dbTv;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static String EXTRA_TVSHOW_DATA = "EXTRA_TVSHOW_DATA";
    public static String EXTRA_TV_ID = "EXTRA_TV_ID";
    ImageView ivFavoriteFalse, ivFavoriteTrue;

    TvShowLocalData tvShowLocalData;
    TvShowData data;
    int dataId;
    String imageData, titleData, descData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        data = getIntent().getParcelableExtra(EXTRA_TVSHOW_DATA);
        dataId = getIntent().getIntExtra(EXTRA_TV_ID, 0);
        ImageView ivMovie = findViewById(R.id.iv_image_detail);
        TextView tvTitle = findViewById(R.id.tv_title_description);
        TextView tvDesc = findViewById(R.id.tv_detail_description);
        ivFavoriteFalse = findViewById(R.id.iv_favorite_false);
        ivFavoriteTrue = findViewById(R.id.iv_favorite_true);

        data = getIntent().getParcelableExtra(EXTRA_TVSHOW_DATA);
        dataId = getIntent().getIntExtra(EXTRA_TV_ID, 0);
        if(dataId == 0) dataId = data.getId();

        dbTv = Room.databaseBuilder(this,
                TvShowCatalogueDatabase.class,"tvshow").allowMainThreadQueries().build();
        tvShowLocalData = dbTv.tvShowDao().loadByDataId(dataId);
        if(tvShowLocalData != null){
            isDataSavaLocal(true);
        } else isDataSavaLocal(false);

        if(data != null){
            imageData = ApiClient.BASE_URL_POSTER_W500 + data.getPosterPath();
            titleData = data.getName();
            descData = data.getOverview();
        } else {
            imageData = ApiClient.BASE_URL_POSTER_W500 + tvShowLocalData.getPosterPath();
            titleData = tvShowLocalData.getTitle();
            descData = tvShowLocalData.getOverview();
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
                tvShowLocalData = new TvShowLocalData();
                tvShowLocalData.setDataId(data.getId());
                tvShowLocalData.setTitle(data.getName());
                tvShowLocalData.setOverview(data.getOverview());
                tvShowLocalData.setPosterPath(data.getPosterPath());

                dbTv.tvShowDao().insertAll(tvShowLocalData);

                isDataSavaLocal(true);
                Toast.makeText(this, "Data berhasil di masukan ke favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_favorite_true:
                dbTv.tvShowDao().deleteByUserId(dataId);

                isDataSavaLocal(false);
                Toast.makeText(this, "Data berhasil di hapus dari favorite", Toast.LENGTH_SHORT).show();

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
