package com.example.jaineek.meeplemain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SettingsActivity extends PreferenceActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private EditTextPreference editTextEmail;
    private EditTextPreference editTextPassword;
    private EditTextPreference editTextUsername;

    private String email;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Using preferences.xml in xml resource folder to draw activity
        addPreferencesFromResource(R.xml.preferences);

        //Firebase references
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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
                                mUser.updateEmail(email);                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
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
                        return false;
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
                                mUser.updateProfile(profileUpdates);                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
    }



}
