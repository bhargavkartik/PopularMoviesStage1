package com.kartik.popularmoviesstage1.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kbhargav on 2/26/2018.
 */

public class SharedPreferencesUtils
{
    public static void updateSharedPreferences(Context context, String shPrefName, String prefName, String prefVal)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefName, prefVal);
        editor.apply();
    }

    public static String readSharedPreferences(Context context, String shPrefName, String prefName)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName, Context.MODE_PRIVATE);
        return sharedPref.getString(prefName, "");
    }
}
