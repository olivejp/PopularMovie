package com.orlanth23.popularmovie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.fragment.MainFragment;

public class MainActivity extends AppCompatActivity{

    private static final String FRAGMENT = "FRAGMENT";
    public static final String MAIN_BACKSTACK = "MAIN_BACKSTACK";

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null && savedInstanceState.containsKey(FRAGMENT)){
            mainFragment = (MainFragment) fm.getFragment(savedInstanceState, FRAGMENT);
        }else{
            mainFragment = new MainFragment();
        }

        if (fm.findFragmentById(R.id.main_container) == null){
            fm.beginTransaction().add(R.id.main_container, mainFragment, MainFragment.TAG).addToBackStack(MAIN_BACKSTACK).commit();
        } else {
            fm.beginTransaction().replace(R.id.main_container, mainFragment, MainFragment.TAG).addToBackStack(MAIN_BACKSTACK).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT, mainFragment);
    }
}
