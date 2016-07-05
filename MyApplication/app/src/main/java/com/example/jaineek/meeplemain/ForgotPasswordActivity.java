package com.example.jaineek.meeplemain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEmailAddress;
    private Button mSendEmail;
    private Context mContext;
    private static final String TAG = "ForgotPasswordActivity";

    //Firebase variable declarations
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Get FireBase auth database instance
        mAuth = FirebaseAuth.getInstance();
        mEmailAddress = (EditText) findViewById(R.id.forgot_password_email_editText);

        mSendEmail = (Button) findViewById(R.id.forgot_password_send_email_button);
        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get context for making a Toast
                mContext = v.getContext();
                sendEmail();
            }
        });
    }


    public void sendEmail() {
        // Send email for password recovery
        String email = mEmailAddress.getText().toString();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Password recovery email sent");

                    // Make a Toast for successful recovery email
                    Toast.makeText(mContext, getString(R.string.toast_recovery_email),
                            Toast.LENGTH_SHORT).show();
                } else {
                    mEmailAddress.setError(getString(R.string.error_invalid_email));
                }
            }
        });
    }
}