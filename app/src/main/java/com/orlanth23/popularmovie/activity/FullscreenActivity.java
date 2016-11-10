package com.orlanth23.popularmovie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;

public class FullscreenActivity extends AppCompatActivity {

    private AppCompatActivity context = this;
    private static int INTENT_CODE_MAIN = 100;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int time = 0;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.pref_key_display_screen), true)) {
            time = 1000;
            setContentView(R.layout.activity_fullscreen);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                launchMainActivity();
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE_MAIN){
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
            finish();
        }
    }

    private void launchMainActivity(){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        startActivityForResult(intent, INTENT_CODE_MAIN);
    }
}
