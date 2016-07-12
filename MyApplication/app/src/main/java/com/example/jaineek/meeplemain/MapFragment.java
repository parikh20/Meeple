package com.example.jaineek.meeplemain;

import android.graphics.drawable.Drawable;
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
    public static String title_map_fragment = "Your Location";
    public static int drawable_icon_map_fragment = R.drawable.ic_location_on_white_48dp;

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

    @Override
    public String getTitle() {
        // Returns title of fragment
        return title_map_fragment;
    }

    @Override
    public Drawable getDrawableIcon() {
        // Returns Drawable tab icon for this page
        return getActivity().getDrawable(drawable_icon_map_fragment);
    }
}
