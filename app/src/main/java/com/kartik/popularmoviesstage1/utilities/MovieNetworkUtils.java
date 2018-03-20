package com.kartik.popularmoviesstage1.utilities;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kartik.popularmoviesstage1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by kbhargav on 2/26/2018.
 */

public class MovieNetworkUtils
{
    private static  final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3";
    final static String ENDPOINT_POPULAR = "movie/popular";
    final static String ENDPOINT_TOP_RATED = "movie/top_rated";
    private final static String MOVIE_POSTER_BASE_URL = "https://image.tmdb.org/t/p";

    static String getMovies(@NonNull String endPoint, Context context) throws IOException
    {
        URL requestURL = buildMovieAPIEndPointURL(endPoint, context);
        return getResponseFromHttpUrl(requestURL);
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try
        {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            throw ioe;
        }
        finally
        {
            urlConnection.disconnect();
        }
    }

    private static URL buildMovieAPIEndPointURL(String endPoint, Context context) throws MalformedURLException
    {
        Uri movieAPIEndPointUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendEncodedPath(endPoint)
                .appendQueryParameter("api_key", context.getString(R.string.api_key))
                .build();

        Log.d("MovieNetworkUtils", movieAPIEndPointUri.toString());
        return new URL(movieAPIEndPointUri.toString());
    }

    public static String buildMoviePosterURL(String sizePart, String picPath) {
        Uri requestUri = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(sizePart)
                .appendEncodedPath(picPath)
                .build();

        Log.d("MovieNetworkUtils", requestUri.toString());
        return requestUri.toString();
    }
}
