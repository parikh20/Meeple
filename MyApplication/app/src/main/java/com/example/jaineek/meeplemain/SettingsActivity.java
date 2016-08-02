package com.example.jaineek.meeplemain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SettingsActivity extends PreferenceActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private EditTextPreference editTextEmail;
    private EditTextPreference editTextPassword;
    private EditTextPreference editTextUsername;
    private EditTextPreference editTextRadius;
    private CheckBoxPreference checkboxPreferenceTheme;
    private SharedPreferences mSharedPreferences;

    private double radius;
    private String email;
    private String password;
    private String username;
    private String theme = "key_change_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();

        if (mSharedPreferences.getBoolean(theme, false)) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);

        //Using preferences.xml in xml resource folder to draw activity
        addPreferencesFromResource(R.xml.preferences);


        //Firebase references
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //On Change listener for changing radius
        editTextRadius = (EditTextPreference) findPreference("key_change_radius");
        editTextRadius.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue) {
                // Change radius if ok is clicked
                ;
                radius = Double.parseDouble((String) newValue);
                System.out.println(radius);
                // Check bounds
                if (radius > 500) {
                    radius = 500;
                } else if (radius < 1 ) {
                    radius = 1;
                }
                // Store the entered value
                editor.putFloat("key_change_radius", (float) radius);
                editor.apply();

                return true;
            }
        });


        //On Change listener for changing email
        editTextEmail = (EditTextPreference) findPreference("key_change_email");
        editTextEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue) {
                //Setting Alert Dialogue for confirmation
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //change email if user clicks ok
                                email = newValue.toString();
                                mUser.updateEmail(email);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

        //On Change listener for changing password EditText
        // TODO: Setup password authentication and pop up confirmation window
        editTextPassword = (EditTextPreference) findPreference("key_change_password");
        editTextPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue) {
                //Set alert dialogue for confirmation
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("Confirm password change")
                        .setMessage("Are you sure you want to change your password?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Change password if user clicks ok
                                password = newValue.toString();
                                mUser.updatePassword(password);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });

        // On change Listener for changing username EditText
        editTextUsername = (EditTextPreference) findPreference("key_change_username");
        editTextUsername.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue) {
                //Set alert dialogue for confirmation
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Change username if user clicks ok
                                username = newValue.toString();
                                UserProfileChangeRequest profileUpdates =
                                        new UserProfileChangeRequest.Builder().setDisplayName(username).build();

                                // Change profile
                                mUser.updateProfile(profileUpdates);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });


        //Change shared preferences switch boolean
        checkboxPreferenceTheme = (CheckBoxPreference) findPreference(theme);
        checkboxPreferenceTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (o instanceof Boolean) {
                    Toast.makeText(SettingsActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                    editor.putBoolean(theme, (Boolean)o);
                    editor.apply();
                    recreate();
                }
                return true;
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}

