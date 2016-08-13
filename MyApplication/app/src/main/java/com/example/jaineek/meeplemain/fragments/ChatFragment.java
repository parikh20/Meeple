package com.example.jaineek.meeplemain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jaineek.meeplemain.ChatActivity;
import com.example.jaineek.meeplemain.NewChatActivity;
import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.adapters_and_holders.ChatInfoViewHolder;
import com.example.jaineek.meeplemain.adapters_and_holders.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatFragment extends Fragment implements MeepleFragment{
    // Displays all chats this User has with other Users

    public static final String TAG = "FRAGMENT_CHAT";
    public static String title_chat_fragment = "Chats";
    public static int drawable_icon_id = R.drawable.ic_people_white_48dp;


    private DatabaseReference mUserConvosRef;
    private RecyclerView mChatsRecyclerView;

    private String mUserUID;
    private FloatingActionButton chatFAB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to all the Convokeys for this User
        mUserConvosRef = FirebaseDatabase.getInstance().getReference(ChatActivity.NODE_USERS)
                .child(mUserUID).child(ChatActivity.NODE_CONVOKEYS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        mChatsRecyclerView = (RecyclerView) v.findViewById(R.id.chats_recyclerView);
        setUpRecyclerViewAndAdapter();

        chatFAB = (FloatingActionButton) v.findViewById(R.id.fab_chats);
        // OnClickListener to launch NewChatActivity
        chatFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start NewChatActivity
                Intent intent = new Intent(view.getContext(), NewChatActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void setUpRecyclerViewAndAdapter() {
        // Sets up RecyclerView, FirebaseRecyclerAdapter with data and LayoutManager

        // Sets linearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mChatsRecyclerView.setLayoutManager(layoutManager);

        // Get only posts for this userUID
        Query chatsForThisUser =  mUserConvosRef;

        // Creates adapter w/ data. Sets up w/ RecyclerView
        FirebaseRecyclerAdapter<String, ChatInfoViewHolder> chatInfoAdapter =
                new FirebaseRecyclerAdapter<String, ChatInfoViewHolder>(String.class,
                        R.layout.custom_view_chat_info, ChatInfoViewHolder.class, chatsForThisUser) {
                    @Override
                    protected void populateViewHolder(ChatInfoViewHolder viewHolder, String convokey,
                                                      int position) {
                        // Populate viewHolder with Chat information
                        viewHolder.bindViewsWithChatInfo(convokey);
                    }
                };
        mChatsRecyclerView.setAdapter(chatInfoAdapter);
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
