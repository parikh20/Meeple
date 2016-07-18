package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaineek.meeplemain.fragments.LocalFeedFragment;


public class NewPostActivity extends AppCompatActivity {
    // Create a new post

    private EditText mTestEditText;
    private Button mTestButton;
    private Location mLocation;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        if (mSharedPreferences.getBoolean("key_change_theme", false)) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        /* ALL TEST STUFF -- CHANGE */

        mTestEditText = (EditText) findViewById(R.id.test_newPost_editText);
        mTestButton = (Button) findViewById(R.id.test_newPost_button);

        // Get user's location from calling Intent
        Intent startedIntent = getIntent();
        mLocation = startedIntent.getParcelableExtra(FeedActivity.KEY_EXTRA_LOCATION);
        if (mLocation != null) {
            String location = "Latitude: " + Double.toString(mLocation.getLatitude()) +
                    " Longitude: " +  Double.toString(mLocation.getLongitude());
            Toast.makeText(NewPostActivity.this, location,
                    Toast.LENGTH_LONG).show();
        }

        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mTestEditText.getText())) {
                    // Display a toast of the message and the location
                    Toast.makeText(NewPostActivity.this, mTestEditText.getText().toString()
                    + ". Location: ", Toast.LENGTH_LONG).show();
                }
            }
        });


        /* ALL TEST STUFF -- CHANGE */
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
