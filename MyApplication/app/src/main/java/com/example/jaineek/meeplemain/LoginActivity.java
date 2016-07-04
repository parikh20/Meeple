package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.net.Uri;
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

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    // All Firebase member variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView mDontHaveAccountClickable;
    private Button mLoginButton;
    private EditText mEmailAddress;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        mDontHaveAccountClickable = (TextView) findViewById(R.id.login_dont_have_account_clickable);

        // Setting onClickListener for create new account clickable
        mDontHaveAccountClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to change to register screen
                Intent changeToRegister = new Intent(v.getContext(), MeepleMain.class);
                startActivity(changeToRegister);
            }
        });

        mEmailAddress = (EditText) findViewById(R.id.login_email_editText);
        mPassword = (EditText) findViewById(R.id.login_password_editText);

        // Wiring Login button
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Authenticate login credentials
                authenticateUser();
            }
        });
    }

    public void authenticateUser() {
        // Attempts to login user with entered Email Address and Password
        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Login successful!
                                Intent changeToRegister = new Intent(LoginActivity.this, MeepleMain.class);
                                startActivity(changeToRegister);
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "Login tried with null entries");
            // If Email Address and Password are null entries
            if (TextUtils.isEmpty(email)) {
            mEmailAddress.setError(getString(R.string.error_required));
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError(getString(R.string.error_required));
            }
        }
    }
}
