package com.example.moviecatalogue.channels;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.moviecatalogue.channels.movie.MovieFragment;
import com.example.moviecatalogue.channels.tvShow.TvShowFragment;

public class ChannelsAdapter extends FragmentStatePagerAdapter {

    private static int TOTAL_PAGE = 2;
    private String tabTitles[] = new String[] { "Movie", "TV Show" };
    private Context context;

    public ChannelsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return MovieFragment.newInstance(0);
            case 1:
                return TvShowFragment.newInstance(1);
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
