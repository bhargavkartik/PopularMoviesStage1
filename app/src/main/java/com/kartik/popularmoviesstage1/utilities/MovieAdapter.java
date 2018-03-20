package com.kartik.popularmoviesstage1.utilities;

import android.support.v4.app.LoaderManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kartik.popularmoviesstage1.R;
import com.kartik.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbhargav on 2/27/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>
{
    private List<Movie> m_moviesList;
    private boolean mIsLoadingNow;
    public static int loadedPageCount = 1;
    private LoaderManager mLoaderManager;
    private boolean mShouldClearList;

    public void setShouldClearList(boolean mShouldClearList) {
        this.mShouldClearList = mShouldClearList;
    }

    public MovieAdapter(List<Movie> movieList, LoaderManager loaderManager) {
        this.m_moviesList = movieList;
        this.mLoaderManager = loaderManager;
    }

    public MovieAdapter(List<Movie> m_moviesList)
    {
        this.m_moviesList = m_moviesList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_movie, parent, false);
        layoutView.setTag(m_moviesList);
        return new MovieViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        //holder.movieTitle.setText(m_moviesList.get(position).getTitle());
        Picasso.with(holder.moviePoster.getContext())
                        .load(MovieNetworkUtils.buildMoviePosterURL("w200", m_moviesList.get(position).getPosterPath()))
                        .into(holder.moviePoster);
    }

    @Override
    public int getItemCount()
    {
        if(m_moviesList == null)
        {
            return 0;
        }
        else
        {
            return m_moviesList.size();
        }
    }

    public void setMovieList(ArrayList<Movie> lst) {
        if(mShouldClearList){
            m_moviesList.clear();
            setShouldClearList(false);
        }
        m_moviesList.addAll(lst);
        notifyDataSetChanged();
        mIsLoadingNow = false;
    }
}
