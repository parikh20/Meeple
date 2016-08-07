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

    public static final String TAG = "FRAGMENT_CHAT";
    public static String title_chat_fragment = "Chats";
    public static int drawable_icon_id = R.drawable.ic_people_white_48dp;

    private Button button;

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
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


    @Override
    public String getTitle() {
        return title_chat_fragment;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getDrawableIconId() {
        return drawable_icon_id;
    }
}
