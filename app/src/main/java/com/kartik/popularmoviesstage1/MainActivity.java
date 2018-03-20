package com.kartik.popularmoviesstage1;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kartik.popularmoviesstage1.model.Movie;
import com.kartik.popularmoviesstage1.utilities.MovieAdapter;
import com.kartik.popularmoviesstage1.utilities.MovieLoader;
import com.kartik.popularmoviesstage1.utilities.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbhargav on 2/19/2018.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks
{
    private ProgressBar m_LoadingIndicator;

    public static final int MOVIE_LOADER_ID = 45;

    private MovieAdapter m_MovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_LoadingIndicator = findViewById(R.id.pb_movies);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Movie>> movieLoader = loaderManager.getLoader(MOVIE_LOADER_ID);
        if(movieLoader == null)
        {
            loaderManager.initLoader(MOVIE_LOADER_ID, new Bundle(), this).forceLoad();
        }
        else
        {
            loaderManager.restartLoader(MOVIE_LOADER_ID, new Bundle(), this).forceLoad();
        }

        List<Movie> moviesList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

        RecyclerView moviesRecyclerView = findViewById(R.id.rv_movies);
        moviesRecyclerView.setLayoutManager(layoutManager);
        m_MovieAdapter = new MovieAdapter(moviesList, loaderManager);
        moviesRecyclerView.setAdapter(m_MovieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movies, menu);
        String previousSortSetting = SharedPreferencesUtils.readSharedPreferences(this,getString(R.string.sharedPrefFileName),getString(R.string.sort_mode));
        switch (previousSortSetting){
            case "Sort movies by popularity":
                menu.findItem(R.id.action_popular).setChecked(true);
                setAppTitle("popular");
                break;
            case "Sort movies by rating":
                menu.findItem(R.id.action_top_rated).setChecked(true);
                setAppTitle("rated");
                break;
            default:
                menu.findItem(R.id.action_popular).setChecked(true);
                setAppTitle("popular");
        }
        return true;
    }

    public void onSortByPopularityMenuItemClick(MenuItem menuItem){
        MovieAdapter.loadedPageCount = 1;
        m_MovieAdapter.setShouldClearList(true);
        m_LoadingIndicator.setVisibility(View.VISIBLE);
        menuItem.setChecked(true);
        setAppTitle("popular");

        SharedPreferencesUtils.updateSharedPreferences(this,
                this.getResources().getString(R.string.sharedPrefFileName),
                this.getResources().getString(R.string.sort_mode),
                this.getResources().getString(R.string.sort_movies_by_popularity));

        getSupportLoaderManager().getLoader(MOVIE_LOADER_ID).forceLoad();
    }

    public void onSortByRatingMenuItemClick(MenuItem menuItem)
    {
        MovieAdapter.loadedPageCount = 1;
        m_MovieAdapter.setShouldClearList(true);
        m_LoadingIndicator.setVisibility(View.VISIBLE);
        menuItem.setChecked(true);
        setAppTitle("rated");

        SharedPreferencesUtils.updateSharedPreferences(this,
                this.getResources().getString(R.string.sharedPrefFileName),
                this.getResources().getString(R.string.sort_mode),
                this.getResources().getString(R.string.sort_movies_by_rating));

        getSupportLoaderManager().getLoader(MOVIE_LOADER_ID).forceLoad();
    }

    private void setAppTitle(String sortType)
    {
        setTitle(getString(R.string.app_name) + " (" + sortType + ")");
    }

    //Loaders
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        m_LoadingIndicator.setVisibility(View.VISIBLE);
        return new MovieLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(data !=  null && data instanceof ArrayList) {
            m_MovieAdapter.setMovieList((ArrayList<Movie>) data);
        }else {
            Toast.makeText(this, "Couldn't fetch the data from the MovieService", Toast.LENGTH_LONG).show();
        }
        m_LoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader)
    {
        // empty method
    }
}
