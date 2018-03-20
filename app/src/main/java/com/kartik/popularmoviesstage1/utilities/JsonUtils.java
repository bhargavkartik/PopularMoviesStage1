package com.kartik.popularmoviesstage1.utilities;

import android.content.Context;

import com.kartik.popularmoviesstage1.R;
import com.kartik.popularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Movie> getMoviesListFromJSON(String jsonSource, Context context) throws JSONException, ParseException {
        List<Movie> movieList = new ArrayList<>();
        JSONObject rootObj = new JSONObject(jsonSource);
        JSONArray moviesJsonArray = rootObj.getJSONArray(context.getString(R.string.json_names_results));

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);
            movieList.add(new Movie(jsonMovie.getInt(context.getString(R.string.json_names_id)),
                    jsonMovie.getString(context.getString(R.string.json_names_title)),
                    jsonMovie.getString(context.getString(R.string.json_names_poster_path)),
                    jsonMovie.getString(context.getString(R.string.json_names_backdrop_path)),
                    jsonMovie.getString(context.getString(R.string.json_names_overview)),
                    (float) jsonMovie.getDouble(context.getString(R.string.json_names_vote_average)),
                    jsonMovie.getString(context.getString(R.string.json_names_release_date))));
        }
        return movieList;
    }
}