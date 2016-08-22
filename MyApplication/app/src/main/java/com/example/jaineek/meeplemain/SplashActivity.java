package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        // Check for dark theme
        if (mSharedPreferences.getBoolean("key_change_theme", false)) {
            setContentView(R.layout.activity_splash_dark);
        } else {
            setContentView(R.layout.activity_splash);
        }

        //Execute Async Task
        new BackgroundSplash().execute();
    }

    private class BackgroundSplash extends AsyncTask<Void, Void, Void> {
        Intent intent;

        //Creates an Intent for Login Activity on the UI thread before Async Task is executed
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        //Putting thread to sleep for 3 seconds in the background thread
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Go to LoginActivity once everything has finished
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            startActivity(intent);
            finish();
        }
    }
}
