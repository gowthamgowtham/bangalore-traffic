package com.gowtham.bangaloretraffic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gowtham on 24-Aug-14.
 */
public class Settings {
    private static Settings ourInstance;
    private static Context ctx;

    private static SharedPreferences preferences;
    public static Settings getInstance() {
        if(ourInstance == null) {
            ourInstance = new Settings();
        }
        return ourInstance;
    }

    private Settings() {
    }

    public static void setContext(Context ctx) {
        Settings.ctx = ctx;
        preferences = ctx.getSharedPreferences("fav", Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addAsFavourite(String name, int id) {
        preferences.edit().putInt(name, id).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void removeFromFavourite(String name) {
        preferences.edit().remove(name).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public java.util.Map<String, ?> getAllFavourites() {
        return preferences.getAll();
    }
}
