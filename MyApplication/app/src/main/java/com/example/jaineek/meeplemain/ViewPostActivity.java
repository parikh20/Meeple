
package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaineek.meeplemain.model.Post;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

public class ViewPostActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String KEY_EXTRA_POST = "com.example.jaineek.meeplemain.extra_tag_post";

    private Post mPost;
    private TextView mPostTitle;
    private TextView mPostLocation;
    private TextView mPostDescription;
    private TextView mPostDate;
    private SimpleDateFormat mSimpleDateFormat;

    private MapView mMapView;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        // Needs to be started with an Extra ALWAYS
        Intent intent = getIntent();
        mPost = intent.getParcelableExtra(KEY_EXTRA_POST);

        // Find Views within itemView
        mPostDescription =(TextView) findViewById(R.id.view_post_description_textView);
        mPostTitle =(TextView) findViewById(R.id.view_post_title_textView);
        mPostLocation = (TextView) findViewById(R.id.view_post_location_textView);
        mPostDate = (TextView) findViewById(R.id.view_post_date_textView);

        mSimpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy  hh:mm aaa");

        mMapView = (MapView) findViewById(R.id.view_post_google_mapView);
        mMapView.onCreate(savedInstanceState);  // Must be forwarded

        // Set onMapCallBack
        mMapView.getMapAsync(this);

        bindViewsWithPost();
    }

    public void bindViewsWithPost() {
        // Update member variables with info from Post

        mPostTitle.setText(mPost.eventTitle);
        mPostDate.setText(mSimpleDateFormat.format(mPost.eventDate));
        mPostLocation.setText(mPost.eventLocation.toString());
        mPostDescription.setText(mPost.eventDesc);
    }

    /* GOOGLE MAPS METHODS */

    @Override
    public void onMapReady(GoogleMap map) {
        // Once map is ready, perform setup
        mMap = map;
        // Enables MyLocation button on map
        if (ContextCompat.checkSelfPermission(ViewPostActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Check if Lcoation services are enabled
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Add marker at location
            double lat = mPost.eventLocation.getLatitude();
            double lon = mPost.eventLocation.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng));

            float zoomLevel = 13; // This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        } else {
            // Notify the user that ACCESS LOCATION permission is disabled
            Toast.makeText(ViewPostActivity.this, getString(R.string.error_location_not_supported),
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