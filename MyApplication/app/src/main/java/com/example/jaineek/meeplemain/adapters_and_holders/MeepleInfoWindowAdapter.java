package com.example.jaineek.meeplemain.adapters_and_holders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Post;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Krishnak97 on 8/1/2016.
 */

public class MeepleInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    // Handles custom popup views for Map Markers

    private LayoutInflater mLayoutInflater;
    private HashMap<Marker, Post> mMarkersToPosts;


    public MeepleInfoWindowAdapter(LayoutInflater layoutInflater, HashMap<Marker,
            Post> markerPostHashMap) {
        mLayoutInflater = layoutInflater;
        mMarkersToPosts = markerPostHashMap;
    }

    private void populateWindowWithPost(final Post post, View infoWindow) {
        // Find Views within infoWindow
        TextView postDescription =(TextView) infoWindow.findViewById(
                R.id.marker_post_description_textView);
        TextView postTitle =(TextView) infoWindow.findViewById(R.id.marker_post_title_textView);
        TextView postLocation = (TextView) infoWindow.findViewById(
                R.id.marker_post_location_textView);
        TextView postDate = (TextView) infoWindow.findViewById(R.id.marker_post_date_textView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy  hh:mm aaa");

        // Fill fields
        postTitle.setText(post.eventTitle);
        postDate.setText(simpleDateFormat.format(post.eventDate));
        postLocation.setText(post.eventLocation.toString());
        postDescription.setText(post.eventDesc);
    }

    /* MAPS INFO WINDOW METHODS */

    @Override
    public View getInfoWindow(Marker marker) {
        // Leave null to keep correct outline
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Custom window for each marker
        Post post = mMarkersToPosts.get(marker);

        View infoWindow = mLayoutInflater.inflate(R.layout.custom_info_window, null);
        populateWindowWithPost(post, infoWindow);

        return infoWindow;

    }
}
