package com.example.moviecatalogue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviecatalogue.channels.ChannelFragment;
import com.example.moviecatalogue.favorite.FavoriteFragment;
import com.example.moviecatalogue.search.SearchActivity;
import com.example.moviecatalogue.setting.SettingActivity;

public class MainActivity extends BaseActivity {

    SearchView searchView;
    String type = "movie";

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.navigation_movies:
                    fragment = new ChannelFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tv_show:
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelected);

        if(savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_TYPE, type);
                        intent.putExtra(SearchActivity.EXTRA_QUERY, query);
                        startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
