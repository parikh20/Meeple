package com.example.jaineek.meeplemain.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jaineek.meeplemain.Manifest;
import com.example.jaineek.meeplemain.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class MyMapFragment extends Fragment implements MeepleFragment,
        OnMapReadyCallback{
    // Container fragment and handler for GoogleMap SupportMapFragment

    public static final String TAG = "FRAGMENT_MAP";
    private static String title_map_fragment = "My Location";
    private static int drawable_icon_id = R.drawable.ic_location_on_white_48dp;

    private MapView mMapView;
    private GoogleMap mMap;

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
        mMapView = (MapView) v.findViewById(R.id.google_mapView);
        mMapView.onCreate(savedInstanceState);  // Must be forwarded

        // Set onMapCallBack
        mMapView.getMapAsync(this);
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

    /* GOOGLE MAPS METHODS */

    @Override
    public void onMapReady(GoogleMap map) {
        // Once map is ready, perform setup
        mMap = map;
        // Enables MyLocation button on map
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Check if Lcoation services are enabled
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        } else {
             // Notify the user that ACCESS LOCATION permission is disabled
            Toast.makeText(getActivity(), getString(R.string.error_location_not_supported),
                    Toast.LENGTH_LONG).show();
        }
    }

    /* LIFECYCLE METHODS  -
    *       MapView requires all lifecycle methods
    *       to be forwarded from the hosting activity /
    *       fragment
    */

    public void onResume() {
        // Must be forwarded
        super.onResume();
        mMapView.onResume();
    }

    public void onDestroy() {
        // Must be forwarded
        super.onDestroy();
        mMapView.onDestroy();
    }

    public void onLowMemory() {
        // Must be forwarded
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
