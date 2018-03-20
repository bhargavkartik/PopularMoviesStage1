package com.kartik.popularmoviesstage1;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kartik.popularmoviesstage1.model.Movie;
import com.kartik.popularmoviesstage1.utilities.MovieNetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by kbhargav on 2/26/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity
{
    public static final String MOVIE_PARCEL = "movieDetails";

    private TextView m_TvTitle;
    private TextView m_TvOverview;
    private TextView m_TvReleaseDate;
    private TextView m_TvVoteAverage;
    private ImageView m_IvBackDrop;
    private ImageView m_IvPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initializeUIElements();

        Intent intent = getIntent();
        if(intent == null)
        {
            closeOnError();
        }

        Movie movie = null;
        try
        {
            movie = intent.getParcelableExtra(MOVIE_PARCEL);
        }
        catch(NullPointerException ne)
        {
            Log.d(getString(R.string.parcelable_exception), Log.getStackTraceString(ne));
            closeOnError();
        }

        populateMovieDetailsUI(movie);
    }

    private void populateMovieDetailsUI(Movie movie)
    {
        m_TvTitle.setText(movie.getTitle());
        m_TvOverview.setText(movie.getOverview());
        Resources res = getResources();
        m_TvReleaseDate.setText(movie.getReleaseDate());
        m_TvVoteAverage.setText(Float.valueOf(movie.getVoteAverage()).toString());

        // Load Backdrop Movie Poster
        try
        {
            Picasso.with(this)
                    .load(MovieNetworkUtils.buildMoviePosterURL("w300", movie.getBackdropPath()))
                    .into(m_IvBackDrop);
        }
        catch (NullPointerException ne)
        {
            Log.d(this.getResources().getString(R.string.picasso_exception), Log.getStackTraceString(ne));
            closeOnError();
        }

        // Load the movie poster
        try
        {
            Picasso.with(this)
                    .load(MovieNetworkUtils.buildMoviePosterURL("w300", movie.getPosterPath()))
                    .into(m_IvPoster);
        }
        catch (NullPointerException ne)
        {
            Log.d(this.getResources().getString(R.string.picasso_exception), Log.getStackTraceString(ne));
            closeOnError();
        }

    }

    private void initializeUIElements()
    {
        m_TvTitle = findViewById(R.id.tv_movie_title);
        m_TvOverview = findViewById(R.id.tv_overview);
        m_TvReleaseDate = findViewById(R.id.tv_release_date);
        m_TvVoteAverage = findViewById(R.id.tv_vote_average);
        m_IvBackDrop = findViewById(R.id.iv_backdrop);
        m_IvPoster = findViewById(R.id.iv_poster);
    }

    private void closeOnError()
    {
        finish();
        Toast.makeText(this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
    }
}
