package com.example.jaineek.meeplemain;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class MeepleMain extends AppCompatActivity {

    private static final String TAG = "MeepleMain";

    private TextView mHaveAccountClickable;
    private Button mRegisterButton;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private android.content.Context mContext;


    // Declaring Firebase variables
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeple_main);

        mHaveAccountClickable = (TextView) findViewById(R.id.register_have_account_clickable);
        //setting onClickListener for have account clickable
        mHaveAccountClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to change back to Login screen
                Intent changeToLogin = new Intent(v.getContext(), LoginActivity.class);
                startActivity(changeToLogin);
            }
        });

        mRegisterButton = (Button) findViewById(R.id.register_button);

        //Setting OnClickListener for Register Button
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                mContext = v.getContext();
            }
        });

    }

    //Reigster user if fields in form are correct
    public void registerUser() {
        //Declaring all EdiText variables
        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
        mPassword = (EditText) findViewById(R.id.register_password_editText);
        mConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_editText);

        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mConfirmPassword.getText().toString();
        //Getting Firebase Authentication Instance
        mAuth = FirebaseAuth.getInstance();


        //Confirm that both password fields contain the same string
        if  (!password.equals(passwordConfirm)) {
            mConfirmPassword.setError("Passwords do not match!");
        //Else, create user
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "registerUser:onComplete: successful");


                            //If Register is successful, take user to LoginActivity
                            if (task.isSuccessful()) {
                                Intent toLoginActivity = new Intent(mContext, LoginActivity.class);
                                startActivity(toLoginActivity);
                            } else {
                                Toast.makeText(MeepleMain.this, "Email already exists in database!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
