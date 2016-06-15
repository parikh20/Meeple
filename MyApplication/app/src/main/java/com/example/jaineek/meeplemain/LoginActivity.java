package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "LoginActivity";

    // all Firebase member variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // m prefix indicates a member object
    private TextView mCreateAccountClick;
    private Button mLoginButton;
    private EditText mUsername;
    private EditText mPassword;

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

        //declaring EditTexts member variables
        mUsername = (EditText) findViewById(R.id.editText_login_username);

        mPassword = (EditText) findViewById(R.id.editText_login_password);

        //wiring login button
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void logIn() {
        Log.d(TAG, "logIn");
        //validates email and password
        if (!validateForm()) {
            return;
        }

//        showProgressDialogue();

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "logIn successful");
//                hideProgressDialogue();

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(LoginActivity.this, R.string.error_login_unsuccessful,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //executes on authentication success
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        writeNewUser(user.getUid(), username, user.getEmail());

        //TODO: start main activity
        // startActivity(new Intent(LoginActivity.this, MainActivity.class);
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;

        //checks if username field is empty
        if (!TextUtils.isEmpty(mUsername.getText().toString())) {
            mUsername.setError("This field is required");
            result = false;
        } else {
            mUsername.setError(null);
        }

        //checks if password field is empty
        if (!TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("This field is required");
            result = false;
        } else {
            mPassword.setError(null);
        }

        return result;
    }

    private void writeNewUser(String userId, String name, String email) {
        Account account = new Account(name, email);

        mDatabase.child("users").child(userId).setValue(account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                logIn();
                break;

            case R.id.dont_have_account_clickable:
                //TODO: intent into register page
                startActivity(new Intent(LoginActivity.this, MeepleMain.class));
                finish();
                break;
        }
    }
}
