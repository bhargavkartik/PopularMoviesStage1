package com.kartik.popularmoviesstage1.utilities;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kartik.popularmoviesstage1.MovieDetailsActivity;
import com.kartik.popularmoviesstage1.R;
import com.kartik.popularmoviesstage1.model.Movie;

import java.util.ArrayList;

/**
 * Created by kbhargav on 2/27/2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ImageView moviePoster;

    public MovieViewHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        moviePoster = itemView.findViewById(R.id.iv_movie_poster_thumbnail);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_PARCEL, ((ArrayList<Movie>) itemView.getTag()).get(getAdapterPosition()));
        Movie clickedMovie = ((ArrayList<Movie>) itemView.getTag()).get(getAdapterPosition());

        Log.d("MovieViewHolder", clickedMovie.getTitle());
        Log.d("MovieViewHolder", clickedMovie.getOverview());
        Log.d("MovieViewHolder", clickedMovie.getBackdropPath());
        Log.d("MovieViewHolder", clickedMovie.getPosterPath());
        Log.d("MovieViewHolder", clickedMovie.getReleaseDate());

        view.getContext().startActivity(intent);
    }
}
