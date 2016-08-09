package com.example.jaineek.meeplemain.adapters_and_holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.ViewPostActivity;
import com.example.jaineek.meeplemain.model.Chat;
import com.example.jaineek.meeplemain.model.Post;

import java.text.SimpleDateFormat;

/**
 * Created by Krishnak97 on 8/8/2016.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    // ChatViewHolder to display each chat bubble's information

    private TextView mChatMessage;
    private TextView mChatTimestamp;
    private Chat mChat;

    public ChatViewHolder(View itemView) {
        super(itemView);
        // ViewHolder now holds custom Chat view (itemView)

        // Find Views within itemView
        mChatMessage = (TextView) itemView.findViewById(R.id.chat_message_textView);

    }

    public void bindViewsWithChat(Chat chat) {
        // Update member variables with info from Chat
        mChat = chat;

        // Display the message text
        mChatMessage.setText(mChat.message);
    }
}
