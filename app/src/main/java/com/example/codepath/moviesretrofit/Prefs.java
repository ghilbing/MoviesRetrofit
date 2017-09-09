package com.example.codepath.moviesretrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.codepath.moviesretrofit.activities.Sort;

/**
 * Created by gretel on 9/8/17.
 */

public class Prefs {
    public static int getCurrentSortMethod(Context context) {
        String key = context.getString(R.string.key_sort_current);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, Sort.POP);
    }

    public static int getPreferredSortMethod(Context context) {
        String key = context.getString(R.string.key_sort_pref);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sharedPreferences.getString(key, "0"));
    }

    public static void setCurrentSortMethod(Context context, int sortMethod) {
        String key = context.getString(R.string.key_sort_current);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, sortMethod).commit();
    }

    public static void setPreferredSortMethod(Context context, int sortMethod) {
        String key = context.getString(R.string.key_sort_pref);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, "" + sortMethod).commit();
    }
}
