package com.example.jaineek.meeplemain.adapters_and_holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaineek.meeplemain.ChatActivity;
import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Krishnak97 on 8/12/2016.
 */

public class ChatInfoViewHolder extends RecyclerView.ViewHolder {
    /* Displays clickable info about a chat with another user.
    Used in a RecyclerView in ChatFragment.
    */

    // TODO: add more member variables
    private TextView mChatMembers;
    private TextView mLastMessageTime;
    private TextView mLastMessageText;
    private ImageView mChatImage;
    private String mConvoKey;

    private SimpleDateFormat mSimpleDateFormat;
    private View mClickableBlock;

    public ChatInfoViewHolder(View itemView) {
        super(itemView);
        // ViewHolder now holds custom Post view (itemView)

        // Find Views within itemView
        mChatMembers =(TextView) itemView.findViewById(R.id.chat_members_textView);
        mLastMessageTime =(TextView) itemView.findViewById(R.id.last_message_time_textView);
        mLastMessageText = (TextView) itemView.findViewById(R.id.last_message_text_textView);
        mChatImage = (ImageView) itemView.findViewById(R.id.chat_imageView);

        mSimpleDateFormat = new SimpleDateFormat("hh:mm aaa");

        // Clickable portion that takes you to the actual Chat
        mClickableBlock = itemView;
        mClickableBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send this Convokey to ChatActivity
                Context viewContext = view.getContext();
                Intent toChatActivity = new Intent(viewContext, ChatActivity.class);
                toChatActivity.putExtra(ChatActivity.KEY_EXTRA_CONVOKEY, mConvoKey);
                viewContext.startActivity(toChatActivity);
            }
        });
    }

    public void bindViewsWithChatInfo(String convokey) {
        // Fills all member views with correct Convo info
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        mConvoKey = convokey;

        // Set mChatMembers to the other User's email
        String otherUserUID = getOtherUserFromConvokey(convokey);
        databaseRef.child(ChatActivity.NODE_USERS).child(otherUserUID).child("email")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mChatMembers.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference thisConvo = databaseRef.child(ChatActivity.NODE_CONVOS)
                .child(convokey);

        Query getLastMessage = thisConvo.orderByChild("timestamp")
                .limitToLast(1);

        // Get values for last message sent
        getLastMessage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat lastMessage = dataSnapshot.getValue(Chat.class);
                if (lastMessage != null) {
                    mLastMessageText.setText(lastMessage.message);
                    mLastMessageTime.setText(mSimpleDateFormat
                            .format(new Date(lastMessage.timeStamp)));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getOtherUserFromConvokey(String convokey) {
        // Returns the other user from the convokey

        String currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String user1 = convokey.substring(0, currentUserUID.length());
        if (user1.equals(currentUserUID)) {
            return convokey.substring(currentUserUID.length() + 1);
        } else {
            return user1;
        }
    }
}
