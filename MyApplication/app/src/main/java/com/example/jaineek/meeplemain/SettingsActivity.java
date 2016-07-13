package com.example.jaineek.meeplemain;

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
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                email = editTextEmail.getText();
                mUser.updateEmail(email);
                return false;
            }
        });

        //On Change listener for changing password EditText
        // TODO: Setup password authentication and pop up confirmation window
        editTextPassword = (EditTextPreference) findPreference("key_change_password");
        editTextPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                password = editTextPassword.getText();
                mUser.updatePassword(password);
                return false;
            }
        });

        // On change Listener for changing username EditText
        editTextUsername = (EditTextPreference) findPreference("key_change_username");
        editTextUsername.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                username = editTextUsername.getText();
                UserProfileChangeRequest profileUpdates =
                        new UserProfileChangeRequest.Builder().setDisplayName(username).build();

                // Change profile
                mUser.updateProfile(profileUpdates);
                return false;
            }
        });
    }



}
