package com.example.moviecataloguefavorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.moviecataloguefavorite.MappingHelper.mapCursorToArrayList;
import static com.example.moviecataloguefavorite.DatabaseContract.NoteColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadMovieDataCallback {

    private DataObserver dataObserver;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(this);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(movieAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor favorite) {
        ArrayList<MovieData> listNotes = mapCursorToArrayList(favorite);
        if (listNotes.size() > 0) {
            movieAdapter.setListNotes(listNotes);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            movieAdapter.setListNotes(new ArrayList<MovieData>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieDataCallback> weakCallback;


        private getData(Context context, LoadMovieDataCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }

    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
