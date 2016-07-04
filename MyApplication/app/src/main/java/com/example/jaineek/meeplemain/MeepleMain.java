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

//import com.firebase.client.Firebase;
//import com.firebase.client.core.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    // Declaring Firebase variables
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeple_main);

        // Getting Firebase Authentication Instance
        mAuth = FirebaseAuth.getInstance();

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
        final String username = mUsername.getText().toString();
        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mConfirmPassword.getText().toString();

        // Confirm that both password fields contain the same string
        if  (!password.equals(passwordConfirm)) {
            mConfirmPassword.setError(getString(R.string.error_passwords_do_not_match));
        // Else, Firebase creates user and adds to database
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "registerUser:onComplete: successful");

                            //If Register is successful, take user to LoginActivity
                            if (task.isSuccessful()) {
                                // Get new user and set Username
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUsername(user, username);

                                // Change to Login screen
                                // TODO: create a private intent function to change activities
                                Intent toLoginActivity = new Intent(mContext, LoginActivity.class);
                                startActivity(toLoginActivity);
                                finish();
                            } else {
                                // Error: account w/ this email already exists
                                Toast.makeText(MeepleMain.this,
                                        getString(R.string.error_email_already_exists),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void updateUsername(FirebaseUser user, String newUsername) {

        // Create request to change profile
        UserProfileChangeRequest profileUpdates =
                new UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername)
                        .build();

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
}
