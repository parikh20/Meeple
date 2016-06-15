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

    private TextView mLoginClick;   // m prefix indicates a member object
    private Button mNextButton;
    private EditText mName;
    private EditText mEmailAddress;
    private EditText mConfirmEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeple_main);

        mLoginClick = (TextView) findViewById(R.id.have_account_clickable);
        //setting onClickListener for have account clickable
        mLoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to change back to Login screen
                Intent changeToLogin = new Intent(v.getContext(), LoginActivity.class);
                startActivity(changeToLogin);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        //setting onClickListener for Next Button
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // check that all fields were filled out
                    assert !mName.getText().toString().equals("");
                    assert !mEmailAddress.getText().toString().equals("");
                    assert !mConfirmEmailAddress.getText().toString().equals("");

                } catch (Exception e) {
                    //create a Toast to alert user of input problem
                    Toast.makeText(MeepleMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* DECLARING ALL INPUT EDITTEXT FIELDS */

        mName = (EditText) findViewById(R.id.editText_name);
        mEmailAddress = (EditText) findViewById(R.id.editText_email);
        mConfirmEmailAddress = (EditText) findViewById(R.id.editText_confirm_email);
    }
}
