package com.example.jaineek.meeplemain;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
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

//import com.firebase.client.Firebase;
//import com.firebase.client.core.Context;
import com.example.jaineek.meeplemain.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class MeepleMain extends AppCompatActivity {

    private static final String TAG = "MeepleMain (Register)";

    private TextView mHaveAccountClickable;
    private Button mRegisterButton;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mUsername;
    private android.content.Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    // Declaring Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        // Check for dark theme
        if (mSharedPreferences.getBoolean("key_change_theme", false)) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeple_main);

        // Getting Firebase Authentication Instance
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mHaveAccountClickable = (TextView) findViewById(R.id.register_have_account_clickable);
        //setting onClickListener for have account clickable
        mHaveAccountClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to change back to Login screen
                Intent changeToLoginScreen = new Intent(v.getContext(), LoginActivity.class);
                startActivity(changeToLoginScreen);
                finish();
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

        // All member variables
        mUsername = (EditText) findViewById(R.id.register_username_editText);
        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
        mPassword = (EditText) findViewById(R.id.register_password_editText);
        mConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_editText);

    }

    // Reigster user if fields in form are correct
    public void registerUser() {

        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();

        // Check that all the fields pass requirements
        if  (registrationFormPassed()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "registerUser:onComplete: successful");

                            String email = mEmailAddress.getText().toString();
                            String password = mPassword.getText().toString();
                            String username = mUsername.getText().toString();

                            //If Register is successful, take user to LoginActivity
                            if (task.isSuccessful()) {

                                // Sign in so getCurrentUser works
                                mAuth.signInWithEmailAndPassword(email, password);

                                // Get new user, set Username, signOut
                                FirebaseUser user = mAuth.getCurrentUser();

                                User userObject = new User(user.getDisplayName(), user.getEmail(), user.getUid());
                                mDatabaseReference.child("users").child(user.getUid()).setValue(userObject);
                                mAuth.signOut();
                                // Change to Login screen
                                // TODO: create a private intent function to change activities
                                Intent toLoginActivity = new Intent(mContext, LoginActivity.class);
                                startActivity(toLoginActivity);
                                finish();
                            } else {
                                // Display proper registration error
                                checkRegistrationError(email, password);
                            }
                        }
                    });
        }
    }

    public boolean registrationFormPassed() {
        /* Checks all fields of registration form for errors.
            Returns true if all ok.*/
        boolean passed = true;

        ArrayList<EditText> viewsToCheck = new ArrayList<>();
        viewsToCheck.add(mPassword);
        viewsToCheck.add(mUsername);
        viewsToCheck.add(mConfirmPassword);
        viewsToCheck.add(mEmailAddress);

        for (EditText form : viewsToCheck) {
            if (TextUtils.isEmpty(form.getText().toString())) {
                // If field is empty, throw error
                form.setError(getString(R.string.error_field_required));
                passed = false;
            }
        }
        // Check that passwords are equal
        if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            passed = false;
            mConfirmPassword.setError("The passwords do not match!");
        }
        return passed;
    }

    public void checkRegistrationError(String email, String password) {
        // Error: Password too short
        if (password.length() < 6) {
            Toast.makeText(MeepleMain.this, getString(R.string
                    .error_password_too_short),
                    Toast.LENGTH_SHORT).show();
        }
        // Error: Account w/ this email already exists
        else {
            Toast.makeText(MeepleMain.this,
                    getString(R.string.error_email_already_exists),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUsername(FirebaseUser user, String newUsername) {

        // Create request to change profile
        UserProfileChangeRequest profileUpdates =
                new UserProfileChangeRequest.Builder().setDisplayName(newUsername).build();

        // Change profile and check if successful
        user.updateProfile(profileUpdates).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "New username was set.");
                    }
                }
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
//        mPassword = (EditText) findViewById(R.id.register_password_editText);
//        mUsername = (EditText) findViewById(R.id.register_username_editText);
//        String savedEmail = mEmailAddress.getText().toString();
//        String savedPassword = mPassword.getText().toString();
//        String savedUsername = mUsername.getText().toString();
//        outState.putString("savedEmail", savedEmail);
//        outState.putString("savedPassword", savedPassword);
//        outState.putString("savedUsername", savedUsername);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
//        mPassword = (EditText) findViewById(R.id.register_password_editText);
//        mUsername = (EditText) findViewById(R.id.register_username_editText);
//        String savedEmail = savedInstanceState.getString("savedEmail", null);
//        String savedPassword = savedInstanceState.getString("savedPassword", null);
//        String savedUsername = savedInstanceState.getString("savedUsername", null);
//        if (savedEmail != null) {
//            mEmailAddress.setText(savedEmail);
//        }
//        if (savedPassword != null) {
//            mPassword.setText(savedPassword);
//        }
//        if (savedUsername != null) {
//            mUsername.setText(savedUsername);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
//        mPassword = (EditText) findViewById(R.id.register_password_editText);
//        mUsername = (EditText) findViewById(R.id.register_username_editText);
//        String savedEmail = mEmailAddress.getText().toString();
//        String savedPassword = mPassword.getText().toString();
//        String savedUsername = mUsername.getText().toString();
//        mEditor.putString("savedEmailMM", savedEmail);
//        mEditor.putString("savedPasswordMM", savedPassword);
//        mEditor.putString("savedUsernameMM", savedUsername);
//        mEditor.apply();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
//        mPassword = (EditText) findViewById(R.id.register_password_editText);
//        mUsername = (EditText) findViewById(R.id.register_username_editText);
//        String savedEmail = mSharedPreferences.getString("savedEmailMM", null);
//        String savedPassword = mSharedPreferences.getString("savedPasswordMM", null);
//        String savedUsername = mSharedPreferences.getString("savedUsernameMM", null);
//        if (savedEmail != null) {
//            mEmailAddress.setText(savedEmail);
//        }
//        if (savedPassword != null) {
//            mPassword.setText(savedPassword);
//        }
//        if (savedUsername != null) {
//            mUsername.setText(savedUsername);
//        }
//    }
}
