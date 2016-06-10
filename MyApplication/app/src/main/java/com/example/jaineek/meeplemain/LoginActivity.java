package com.example.jaineek.meeplemain;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    TextView to_register_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        to_register_activity = (TextView) findViewById(R.id.dont_have_account_clickable);
        to_register_activity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), MeepleMain.class);
                startActivity(registerIntent);
            }
        });
    }


}
