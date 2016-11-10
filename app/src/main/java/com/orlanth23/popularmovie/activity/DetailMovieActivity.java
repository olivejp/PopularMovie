package com.orlanth23.popularmovie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.fragment.DetailMovieFragment;

public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.activity_detail_movie) == null){
            DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
            detailMovieFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction().add(R.id.activity_detail_movie, detailMovieFragment, DetailMovieFragment.TAG).commit();
        }
    }
}
