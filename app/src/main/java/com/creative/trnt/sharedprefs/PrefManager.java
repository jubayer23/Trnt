package com.creative.trnt.sharedprefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.creative.trnt.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Gson GSON = new Gson();
    // Sharedpref file name
    private static final String PREF_NAME = "com.creative.trnt";

    private static final String KYE_MOVIE = "pillars_obj";



    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }



    public void setWishListMovies(List<Movie> obj) {
        editor = pref.edit();

        editor.putString(KYE_MOVIE, GSON.toJson(obj));

        // commit changes
        editor.commit();
    }

    public void setWishListMovies(String obj) {
        editor = pref.edit();

        editor.putString(KYE_MOVIE, obj);

        // commit changes
        editor.commit();
    }


    public List<Movie> getWishListMovies() {

        List<Movie> productFromShared = new ArrayList<>();

        String gson = pref.getString(KYE_MOVIE, "");

        if (gson.isEmpty()) return productFromShared;

        Type type = new TypeToken<List<Movie>>() {
        }.getType();
        productFromShared = GSON.fromJson(gson, type);

        return productFromShared;
    }


}