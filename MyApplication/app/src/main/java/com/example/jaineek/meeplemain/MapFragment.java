package com.example.jaineek.meeplemain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class MapFragment extends Fragment implements MeepleFragment {

    public static final String TAG_MAP = "FRAGMENT_MAP";
    public static String title_map_fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fragment title
        title_map_fragment = getString(R.string.title_map_fragment);

        // Save all instance information

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Declare all mVariables

        return v;
    }

    @Override
    public String getTitle() {
        // Returns title of fragment
        return title_map_fragment;
    }
}
