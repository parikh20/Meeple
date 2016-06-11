package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView mCreateAccountClick;   // m prefix indicates a member object
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCreateAccountClick = (TextView) findViewById(R.id.dont_have_account_clickable);

        //setting onClickListener for create new account clickable
        mCreateAccountClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to change to register screen 1
                Intent changeToRegister1 = new Intent(v.getContext(), MeepleMain.class);
                startActivity(changeToRegister1);
            }
        });

    }


}
