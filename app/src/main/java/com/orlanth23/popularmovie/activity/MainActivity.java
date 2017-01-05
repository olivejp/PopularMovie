package com.orlanth23.popularmovie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.fragment.DetailMovieFragment;
import com.orlanth23.popularmovie.utils.ConfSingleton;

public class MainActivity extends AppCompatActivity{

    public static final String MAIN_BACKSTACK = "MAIN_BACKSTACK";
    public static final String DETAILFRAGMENT_TAG = "DETAILFRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        ConfSingleton.getInstance();
        ConfSingleton.setTwoPane(findViewById(R.id.fragment_detail_container) != null);

        if (ConfSingleton.isTwoPane()) {
            if (savedInstanceState == null) {
                fm.beginTransaction()
                        .replace(R.id.fragment_detail_container, new DetailMovieFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        }
    }
}
