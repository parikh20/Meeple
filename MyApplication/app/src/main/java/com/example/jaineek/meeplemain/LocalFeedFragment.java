package com.example.jaineek.meeplemain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class LocalFeedFragment extends Fragment implements MeepleFragment{

    public static final String TAG_LOCAL_FEED = "FRAGMENT_LOCAL_FEED";
    public static String title_local_feed_fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title_local_feed_fragment = getString(
                R.string.title_local_feed_fragment);
        // Save all instance information

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_local_feed, container, false);


        // Declare all mVariables

        return v;
    }

    public String getTitle() {
        // returns Title of fragment
        return title_local_feed_fragment;
    }
}
