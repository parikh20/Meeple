package com.example.jaineek.meeplemain.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaineek.meeplemain.R;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class MapFragment extends Fragment implements MeepleFragment {

    public static final String TAG = "FRAGMENT_MAP";
    public static String title_map_fragment = "My Location";
    public static int drawable_icon_id = R.drawable.ic_location_on_white_48dp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Save all instance information

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Declare all mVariables

        return v;
    }

    /* MEEPLE FRAGMENT METHODS */

    @Override
    public String getTitle() {
        // Returns title of fragment
        return title_map_fragment;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getDrawableIconId() {
        // Returns Drawable tab icon for this page
        return drawable_icon_id;
    }
}
