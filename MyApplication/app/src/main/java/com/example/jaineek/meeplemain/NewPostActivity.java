package com.example.jaineek.meeplemain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewPostActivity extends AppCompatActivity {
    // Create a new post

    private EditText mTestEditText;
    private Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        /* ALL TEST STUFF -- CHANGE */

        mTestEditText = (EditText) findViewById(R.id.test_newPost_editText);
        mTestButton = (Button) findViewById(R.id.test_newPost_button);

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
}
