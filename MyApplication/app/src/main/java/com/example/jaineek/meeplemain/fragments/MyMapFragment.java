package com.example.jaineek.meeplemain.fragments;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jaineek.meeplemain.FeedActivity;
import com.example.jaineek.meeplemain.Manifest;
import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Post;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jaineek.meeplemain.FeedActivity.PATH_TO_GEOFIRE;
import static com.example.jaineek.meeplemain.FeedActivity.PATH_TO_POSTS;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class MyMapFragment extends Fragment implements MeepleFragment,
        OnMapReadyCallback, ValueEventListener {
    // Container fragment and handler for GoogleMap SupportMapFragment

    public static final String TAG = "FRAGMENT_MAP";
    private static String title_map_fragment = "My Location";
    private static int drawable_icon_id = R.drawable.ic_location_on_white_48dp;


    private MapView mMapView;
    private GoogleMap mMap;
    private Location mLastLocation;
    private SharedPreferences mSharedPreferences;

    private DatabaseReference mPostsReference;
    private DatabaseReference mGeoFireReference;
    private double queryRadius;

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

        mPostsReference = FirebaseDatabase.getInstance().getReference().child(PATH_TO_POSTS);
        mGeoFireReference = FirebaseDatabase.getInstance().getReference().child(PATH_TO_GEOFIRE);

        // Save radius
        mSharedPreferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);
        queryRadius = mSharedPreferences.getFloat("key_change_radius", (float) FeedActivity.DEFAULT_RADIUS);

        // Create geoQuery for user location
        mLastLocation = ((FeedActivity) getActivity()).getmLastLocation();

        // Set onMapCallBack
        mMapView.getMapAsync(this);

        return v;
    }

    private void setupGeoQueryWithMarkers(GeoQuery geoQuery) {
        // Adds event listeners for drawing markers
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                mPostsReference.child(key).addValueEventListener(MyMapFragment.this);
            }

            @Override
            public void onKeyExited(String key) {
                mPostsReference.child(key).removeEventListener(MyMapFragment.this);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                onKeyExited(key);
                onKeyEntered(key, location);
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    /* FIREBASE VALUE EVENT LISTENER METHODS */

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Draw map marker
        // TODO: create custom info window for each marker
        Post post = dataSnapshot.getValue(Post.class);
        double lat = post.eventLocation.getLatitude();
        double lon = post.eventLocation.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(post.eventTitle).snippet(post.eventDesc));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

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

            GeoLocation geoCenter = new GeoLocation(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
            GeoFire geoFire = new GeoFire(mGeoFireReference);
            GeoQuery geoQuery = geoFire.queryAtLocation(geoCenter, queryRadius);

            setupGeoQueryWithMarkers(geoQuery);

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
