package com.example.jaineek.meeplemain.fragments;

import com.example.jaineek.meeplemain.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class MyMapFragment extends MapFragment implements MeepleFragment {
    // Container fragment and handler for GoogleMap SupportMapFragment

    public static final String TAG = "FRAGMENT_MAP";
    private static String title_map_fragment = "My Location";
    private static int drawable_icon_id = R.drawable.ic_location_on_white_48dp;

    private SupportMapFragment mSupportMapFragment;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Save all instance information
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_map, container, false);
//
//        // Declare all mVariables
//
//        return v;
//    }

    public void setSupportMapFragment(SupportMapFragment supportMapFragment) {
        mSupportMapFragment = supportMapFragment;
    }

    public SupportMapFragment getSupportMapFragment() {
        return mSupportMapFragment;
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
