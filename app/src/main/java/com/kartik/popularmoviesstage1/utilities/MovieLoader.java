package com.kartik.popularmoviesstage1.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.kartik.popularmoviesstage1.R;
import com.kartik.popularmoviesstage1.model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by kbhargav on 2/28/2018.
 */

public class MovieLoader extends android.support.v4.content.AsyncTaskLoader<List<Movie>>
{
    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    public List<Movie> loadInBackground()
    {
        String movieSortSetting = SharedPreferencesUtils.readSharedPreferences(getContext(),
                getContext().getResources().getString(R.string.sharedPrefFileName),
                getContext().getResources().getString(R.string.sort_mode));

        String requestEndPoint;
        if(movieSortSetting.equals(getContext().getString(R.string.sort_movies_by_rating)))
        {
            requestEndPoint = MovieNetworkUtils.ENDPOINT_TOP_RATED;
        }
        else
        {
            requestEndPoint = MovieNetworkUtils.ENDPOINT_POPULAR;
        }

        // fetching the movies from the server
        try
        {
            String movieJsonResponse = MovieNetworkUtils.getMovies(requestEndPoint, getContext());
            if(!TextUtils.isEmpty(movieJsonResponse))
            {
                return JsonUtils.getMoviesListFromJSON(movieJsonResponse, getContext());
            }
        }
        catch (JSONException je)
        {
            Log.d(getContext().getString(R.string.json_exception), je.getMessage() + "\n" + Log.getStackTraceString(je));
        }
        catch (ParseException pe)
        {
            Log.d(getContext().getString(R.string.parse_exception), pe.getMessage() + "\n" + Log.getStackTraceString(pe));
        }
        catch (IOException ioe)
        {
            Log.d(getContext().getString(R.string.io_exception), ioe.getMessage() + "\n" + Log.getStackTraceString(ioe));
        }

        return null;
    }
}
