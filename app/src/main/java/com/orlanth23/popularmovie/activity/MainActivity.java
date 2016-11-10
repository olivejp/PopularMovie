package com.orlanth23.popularmovie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;
import com.orlanth23.popularmovie.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.main_container) == null){
            fragmentManager.beginTransaction().add(R.id.main_container, new MainFragment(), MainFragment.TAG).commit();
        }
    }
}
