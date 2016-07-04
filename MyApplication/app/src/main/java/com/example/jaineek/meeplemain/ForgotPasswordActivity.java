package com.example.jaineek.meeplemain;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    //Firebase variable declarations
    private FirebaseAuth mAuth;

    //Other variable declarations
    private EditText mEmail;
    private Button mSendEmail;
    private static final String LOG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.forgot_password_email_editText);
        mSendEmail = (Button) findViewById(R.id.forgot_password_send_email_button);

        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }

    //Send email for password recovery
    public void sendEmail() {
        String email = mEmail.getText().toString();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG, "Password recovery email sent");
                }
            }
        });
    }
}
