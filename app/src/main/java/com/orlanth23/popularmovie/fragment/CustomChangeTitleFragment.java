package com.orlanth23.popularmovie.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;

// This custom fragment adds a new method to Fragment class. Then we can change title depending on preferences.
public abstract class CustomChangeTitleFragment extends Fragment{
    protected void changeTitleFragment(){
        // change title depending on the preference
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            switch (prefs.getString(getString(R.string.pref_key_list_sort), getString(R.string.popularMovies))){
                case "popularmovie":
                    actionBar.setTitle(R.string.pref_popular_movies_title);
                    break;
                case "topRated":
                    actionBar.setTitle(R.string.pref_top_rated_title);
                    break;
            }
        }
    }
}
