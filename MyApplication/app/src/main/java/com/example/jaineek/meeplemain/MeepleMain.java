package com.example.jaineek.meeplemain;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class MeepleMain extends Activity implements View.OnClickListener{

    TextView to_login_activity;
    Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeple_main);

        to_login_activity = (TextView)findViewById(R.id.to_login_text);
        to_login_activity.setOnClickListener(this);

        next_button= (Button)findViewById(R.id.next_button);
        next_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_login_text:
                Intent toLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(toLoginActivity);
                break;

            case R.id.next_button:
                Intent nextRegisterPage = new Intent(getApplicationContext(), MeepleMain2.class);
                startActivity(nextRegisterPage);
                break;
        }
    }
}
