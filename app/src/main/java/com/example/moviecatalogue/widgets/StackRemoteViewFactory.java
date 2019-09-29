package com.example.moviecatalogue.widgets;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.local.database.MovieCatalogueDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<String> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private MovieCatalogueDatabase movieCatalogueDatabase;

    public StackRemoteViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        movieCatalogueDatabase = Room.databaseBuilder(mContext, MovieCatalogueDatabase.class, "movies").allowMainThreadQueries().build();
        mWidgetItems = movieCatalogueDatabase.moviesDao().selectMovieFavorite();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(mWidgetItems.get(position))
                    .apply(requestOptions)
                    .submit()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);
        Bundle extras = new Bundle();
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.stack_view, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
