package com.orlanth23.popularmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.orlanth23.popularmovie.R;

public class FullscreenActivity extends AppCompatActivity {

    private static int INTENT_CODE_MAIN = 100;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        int time = 0;

        // if the pref_key_display_screen is true, then we show the FullScreenActivity for 1500 milliseconds,
        // otherwise the handler is called with a time = 0 and then go directly to the MainActivity.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.pref_key_display_screen), true)) {
            time = 1500;
            setContentView(R.layout.activity_fullscreen);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                // we launch the MainActivity
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivityForResult(intent, INTENT_CODE_MAIN);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // when we get back from the MainActivity, then we remove callback otherwise we will re launch the MainActivity
        if (requestCode == INTENT_CODE_MAIN){
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
            finish();
        }
    }
}
