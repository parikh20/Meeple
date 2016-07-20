package com.example.jaineek.meeplemain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jaineek.meeplemain.model.MeepleLocation;
import com.example.jaineek.meeplemain.model.Post;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NewPostActivity extends AppCompatActivity {
    // Create a new post

    private EditText mEventTitle;
    private EditText mEventDescription;
    private EditText mEventLocation;
    private EditText mEventDateField;

    private Date mEventDate;
    // Standardized formatting for all dates
    private SimpleDateFormat mSimpleDateFormat;

    private Button mPostButton;
    private MeepleLocation mLocation;
    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        if (mSharedPreferences.getBoolean("key_change_theme", false)) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        mPostButton = (Button) findViewById(R.id.new_post_button);
        mEventTitle = (EditText) findViewById(R.id.new_post_event_title_editText);
        mEventDateField = (EditText) findViewById(R.id.new_post_date_editText);
        mEventDescription = (EditText) findViewById(R.id.new_post_description_editText);
        mEventLocation = (EditText) findViewById(R.id.new_post_location_editText);

        mEventDate = new Date();    // Current date and time
        mSimpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy  hh:mm aaa");
        // Init with current date
        mEventDateField.setText(mSimpleDateFormat.format(mEventDate));

        // Get user's location from calling Intent
        Intent startedIntent = getIntent();
        Location lastLocation = startedIntent.getParcelableExtra(FeedActivity.KEY_EXTRA_LOCATION);
        mLocation = new MeepleLocation(lastLocation);
        if (mLocation != null) {
            String location = "Latitude: " + Double.toString(mLocation.getLatitude()) +
                    " Longitude: " +  Double.toString(mLocation.getLongitude());
            Toast.makeText(NewPostActivity.this, location,
                    Toast.LENGTH_LONG).show();
//            mEventLocation.setText(mLocation.toString());
//            TODO: set text of mEventLocation and remove Toast
        }

        setupDateAndTimeDialogues();

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFormPassed()) {
                    // Create a post and push to the database
                    Post newPost = createNewPost();
                    String postKey = mDatabaseReference.child("posts").push().getKey();
                    mDatabaseReference.child("posts").child(postKey).setValue(newPost);

                    // Add to GeoFire ref
                    GeoFire geoFire = new GeoFire(mDatabaseReference.child("geoFire"));
                    GeoLocation postGeoLocation = new GeoLocation(
                            newPost.eventLocation.getLatitude(), newPost.eventLocation.getLongitude());
                    // Create GeoLocation (lat, lon) from post location
                    geoFire.setLocation(postKey, postGeoLocation);

                    //Go back to LocalFeed
                    Intent intent = new Intent(NewPostActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private Post createNewPost() {
        String eventTitle = mEventTitle.getText().toString();
        String eventDesc = mEventDescription.getText().toString();
        String userUID = mAuth.getCurrentUser().getUid();
        Post post = new Post(userUID, eventTitle, eventDesc, mEventDate, mLocation);
        return post;
    }

    private void setupDateAndTimeDialogues() {
        // Sets up DatePicker and TimePicker for mEventDate

        // Create DatePicker for mEventDate
        mEventDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When Date is clicked, show DatePicker
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewPostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                // When date is selected, set calendar
                                calendar.set(Calendar.YEAR, y);
                                calendar.set(Calendar.MONTH, m);
                                calendar.set(Calendar.DAY_OF_MONTH, d);

                                // Show TimePicker

                                TimePickerDialog timePickerDialog = getTimePickerDialog(calendar,
                                        datePicker);
                                timePickerDialog.setTitle(getString(R.string.time_picker_title));
                                timePickerDialog.show();

                            }
                        }, year, month, day);

                // Set title and show it
                datePickerDialog.setTitle(getString(R.string.date_picker_title));
                datePickerDialog.show();
            }
        });
    }

    private TimePickerDialog getTimePickerDialog(Calendar calendar, final DatePicker datePicker) {
        // Setup TimePicker for event
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                NewPostActivity.this, new TimePickerDialog
                .OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                // Confirm both Date and time
                final Date tempDate = getDateFromPickers(datePicker, h, m);

                AlertDialog.Builder confirmBuilder = new
                        AlertDialog.Builder(NewPostActivity.this);
                confirmBuilder.setTitle(getString(
                        R.string.confirm_date_and_time_title))
                        .setMessage(mSimpleDateFormat.format(tempDate))
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Confirm: correct. Set event's date.
                                        mEventDate = tempDate;
                                        mEventDateField.setText(mSimpleDateFormat.format(mEventDate));
                                    }
                                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Confirm: not correct. Restart picking process
                                mEventDateField.performClick();
                            }
                        }).show();
            }
        }, hour, minute, true);

        return timePickerDialog;
    }

    public static java.util.Date getDateFromPickers(DatePicker datePicker, int hour, int minute) {
        // Calculate date based on date and time chosen
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar.getTime();
    }

    private boolean checkFormPassed() {
        /* Checks all fields of new post form for errors.
            Returns true if all ok.*/
        boolean passed = true;

        ArrayList<EditText> viewsToCheck = new ArrayList<>();
        viewsToCheck.add(mEventTitle);
        viewsToCheck.add(mEventDateField);
        viewsToCheck.add(mEventLocation);
        viewsToCheck.add(mEventDescription);
        viewsToCheck.add(mEventDateField);

        for (EditText form : viewsToCheck) {
            if (TextUtils.isEmpty(form.getText())) {
                // If field is empty, throw error
                form.setError(getString(R.string.error_field_required));
                passed = false;
            }
        }
        return passed;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
