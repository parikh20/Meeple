package com.example.jaineek.meeplemain;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jaineek.meeplemain.adapters_and_holders.ChatArrayAdapter;
import com.example.jaineek.meeplemain.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private Button mSendButton;
    private EditText mEditTextMessage;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private final static String NODE_CONVOS = "convos";
    private final static String NODE_USERS = "users";
    private final static String NODE_CONVOKEYS = "convokeys";
    private String mConvokey;
    private Long mTimeStamp;
    private Date mDate;
    private Chat mChat;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mSendButton = (Button) findViewById(R.id.chat_activity_send_button);
        mEditTextMessage = (EditText) findViewById(R.id.chat_activity_edit_text);
        mAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.listView1);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_sent);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declaring chat object, convokey, and timestamp
                mDate = new Date();
                mTimeStamp = mDate.getTime();
                mChat = new Chat(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getUid(), mEditTextMessage.getText().toString(), mTimeStamp);
                mConvokey = mAuth.getCurrentUser().getUid().substring(0,10) + "," + mAuth.getCurrentUser().getUid().substring(0,10);

                //Pushing data to Database
                mDatabaseReference.child(NODE_CONVOS).child(mConvokey).push().setValue(mChat);
                mDatabaseReference.child(NODE_USERS).child(mAuth.getCurrentUser().getUid()).child(NODE_CONVOKEYS).child(mConvokey).setValue(mConvokey);

                //Clearing the edit text
                mEditTextMessage.setText("");
                sendChatMessage();
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto back.
                onBackPressed();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean sendChatMessage(){
        chatArrayAdapter.add("string");
        return true;
    }

}
