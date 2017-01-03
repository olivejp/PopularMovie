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
    public static final String DETAIL_FRAGMENT_BUNDLE = "DETAIL_FRAGMENT_BUNDLE";

    private DetailMovieFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        ConfSingleton.getInstance().setTwoPane(findViewById(R.id.fragment_detail_container) != null);

        if (ConfSingleton.getInstance().isTwoPane()) {

            detailFragment = new DetailMovieFragment();
            if (savedInstanceState == null) {
                fm.beginTransaction()
                        .replace(R.id.fragment_detail_container, detailFragment, DETAILFRAGMENT_TAG)
                        .commit();
            } else {
                if (savedInstanceState.containsKey(DETAIL_FRAGMENT_BUNDLE)){
                    detailFragment = (DetailMovieFragment) fm.getFragment(savedInstanceState, DETAIL_FRAGMENT_BUNDLE);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, FRAGMENT, mainFragment);
        if(ConfSingleton.getInstance().isTwoPane()){
            if (getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_BUNDLE) != null){
                getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT_BUNDLE, detailFragment);
            }
        }
    }
}
