package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.android.popularmovies.utilities.APIUtils;

import org.json.JSONArray;
import org.json.JSONException;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String LOG_TAG = MovieAsyncTaskLoader.class.getName();
    private String endpoint;

    public MovieAsyncTaskLoader(Context context, String endpoint) {
        super(context);
        this.endpoint = endpoint;
        Log.v(LOG_TAG, "Creating a MovieAsyncTaskLoader instance");
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "onStartLoading() happening now");
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        Log.v(LOG_TAG, "Running background task...");

        if (this.endpoint == null) {
            return null;
        }
        JSONArray movies;
        switch (this.endpoint) {
            case APIUtils.POPULAR_ENDPOINT:
                movies = APIUtils.getPopularMovies(getContext().getString(R.string.API_KEY));
                break;
            case APIUtils.TOP_RATED_ENDPOINT:
                movies = APIUtils.getTopRatedMovies(getContext().getString(R.string.API_KEY));
                break;
            default:
                movies = null;
                break;
        }
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < movies.length(); i ++) {
            try {
                movieList.add(new Movie(movies.getJSONObject(i)));
            } catch (JSONException e) {
                Log.d(LOG_TAG, "Not a valid movie.");
                e.printStackTrace();
            }
        }
        return movieList;
    }
}