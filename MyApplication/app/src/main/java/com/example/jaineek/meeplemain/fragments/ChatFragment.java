package com.example.jaineek.meeplemain.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jaineek.meeplemain.ChatActivity;
import com.example.jaineek.meeplemain.NewChatActivity;
import com.example.jaineek.meeplemain.R;

public class ChatFragment extends Fragment implements MeepleFragment{
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        button = (Button) v.findViewById(R.id.button_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewChatActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


    @Override
    public String getTitle() {
        return "Chats";
    }

    @Override
    public String getFragmentTag() {
        return "CHAT_FRAGMENT";
    }

    @Override
    public int getDrawableIconId() {
        return R.drawable.ic_play_dark;
    }
}
