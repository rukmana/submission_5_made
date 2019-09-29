package com.example.moviecatalogue.favorite;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.moviecatalogue.favorite.moviesFavorite.MoviesFavoriteFragment;
import com.example.moviecatalogue.favorite.tvShowFavorite.TvShowFavoriteFragment;


public class FavoriteAdapter extends FragmentStatePagerAdapter {

    private static int TOTAL_PAGE = 2;
    private String tabTitles[] = new String[] { "Movie", "TV Show" };
    private Context context;

    public FavoriteAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return MoviesFavoriteFragment.newInstance(0);
            case 1:
                return TvShowFavoriteFragment.newInstance(1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
