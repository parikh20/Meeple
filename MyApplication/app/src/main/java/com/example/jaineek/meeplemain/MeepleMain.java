package com.example.jaineek.meeplemain;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class MeepleMain extends AppCompatActivity {

    private TextView mHaveAccountClickable;
    private Button mRegisterButton;
    private EditText mEmailAddress;
    private EditText mPassword;
    private EditText mConfirmPassword;

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
        //setting onClickListener for Next Button
//        mRegisterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    // check that all fields were filled out
//                    assert !mName.getText().toString().equals("");
//                    assert !mEmailAddress.getText().toString().equals("");
//                    assert !mConfirmPassword.getText().toString().equals("");
//
//                } catch (Exception e) {
//                    //create a Toast to alert user of input problem
//                    Toast.makeText(MeepleMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        /* DECLARING ALL INPUT EDITTEXT FIELDS */
        mEmailAddress = (EditText) findViewById(R.id.register_email_editText);
        mPassword = (EditText) findViewById(R.id.register_password_editText);
        mConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_editText);
    }
}
