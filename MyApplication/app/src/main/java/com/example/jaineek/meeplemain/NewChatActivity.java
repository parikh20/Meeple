package com.example.jaineek.meeplemain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewChatActivity extends AppCompatActivity {
    //Declaring private variables
    private AutoCompleteTextView mAutoCompleteTextView;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.new_chat_autocomplete);
        //Creating an ArrayAdapter that uses android simple list item 1
        final ArrayAdapter<String> autoCompleteSuggestions = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        //Added ValueEventListener for child users
        mDatabaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("email").getValue(String.class);
                    //Add the retrieved string to the list
                    autoCompleteSuggestions.add(suggestion);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mAutoCompleteTextView.setAdapter(autoCompleteSuggestions);
    }
}
