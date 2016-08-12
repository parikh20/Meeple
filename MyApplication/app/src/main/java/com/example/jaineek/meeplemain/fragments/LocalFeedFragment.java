package com.example.jaineek.meeplemain.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaineek.meeplemain.FeedActivity;
import com.example.jaineek.meeplemain.NewPostActivity;
import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.adapters_and_holders.GeoFireRecyclerAdapter;
import com.example.jaineek.meeplemain.adapters_and_holders.PostViewHolder;
import com.example.jaineek.meeplemain.model.Post;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class LocalFeedFragment extends Fragment implements MeepleFragment {

    public static final String TAG = "FRAGMENT_LOCAL_FEED";
    public static String title_local_feed_fragment = "My Feed";
    public static int drawable_icon_id = R.drawable.ic_home_white_48dp;

    private SharedPreferences mSharedPreferences;
    private RecyclerView mLocalFeedRecyclerView;
    private FloatingActionButton mNewPostFAB;

    // Declaring Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mPostsReference;

    // GeoFire variables
    private GeoFireRecyclerAdapter<Post, PostViewHolder> mAdapter;
    private static double DEFAULT_RADIUS = 100; // Default query radius in km
    private GeoFire mGeoFire;
    private double queryRadius;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Save radius
        mSharedPreferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);
        queryRadius = mSharedPreferences.getFloat("key_change_radius", (float) DEFAULT_RADIUS);

        // Save all instance information
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_local_feed, container, false);

        // Setup Firebase and GeoFire
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mGeoFire = new GeoFire(mDatabaseReference.child(FeedActivity.PATH_TO_GEOFIRE));
        mPostsReference = mDatabaseReference.child(FeedActivity.PATH_TO_POSTS);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mLocalFeedRecyclerView = (RecyclerView) v.findViewById(R.id.local_feed_recyclerView);

        setUpRecyclerViewAndAdapter();

        mNewPostFAB = (FloatingActionButton) v.findViewById(R.id.fab_new_post);
        setUpFloatingActionButton();

        return v;
    }

    private void setUpRecyclerViewAndAdapter() {
        // Sets up RecyclerView, LocalPostAdapter with data and LayoutManager

        // Sets linearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mLocalFeedRecyclerView.setLayoutManager(layoutManager);

        // Setup GeoQuery with last known user GeoLocation
        Location lastLocation = ((FeedActivity) getActivity()).getmLastLocation();
        GeoLocation center;

        if (lastLocation != null) {
            center = new GeoLocation(lastLocation.getLatitude(),
                    lastLocation.getLongitude());
        } else {
            // Use a default location
            center = new GeoLocation(33, -111);
        }

        GeoQuery geoQuery = mGeoFire.queryAtLocation(center, queryRadius);

        // Create a new Adapter for this geoQuery
        mAdapter = new GeoFireRecyclerAdapter<Post, PostViewHolder>(
                Post.class, R.layout.custom_view_post, PostViewHolder.class, mPostsReference,
                geoQuery) {

            @Override
            protected void populateViewHolder(PostViewHolder postViewHolder, Post post,
                                              int position) {
                if (post != null) {
                    // Populate views from custom views
                    postViewHolder.bindViewsWithPost(post);
                }
            }
        };

        mLocalFeedRecyclerView.setAdapter(mAdapter);
    }

    private void setUpFloatingActionButton() {
        // Wires the NewPost floating action button

        mNewPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When clicked, start NewPostActivity
                Intent toNewPostActivity = new Intent(getActivity(), NewPostActivity.class);
                Location location = ((FeedActivity) getActivity()).getmLastLocation();
                // Send user's location to New Post activity
                toNewPostActivity.putExtra(FeedActivity.KEY_EXTRA_LOCATION, location);
                startActivity(toNewPostActivity);
            }
        });
    }

//    private List<Post> findLocalPosts() {
//        // Returns List<Post> close to user locaton
//        // TODO: implement actual GPS tracking
//        List<Post> testPosts = new ArrayList<>();
//        for(int i = 0; i < 30; i++) {
//            Post post = new Post();
//            post.eventDesc = "Local Post " + i;
//            testPosts.add(post);
//        }
//
//        return testPosts;
//    }

    /* MEEPLE FRAGMENT METHODS */

    @Override
    public String getTitle() {
        // returns Title of fragment
        return title_local_feed_fragment;
    }

    @Override
    public int getDrawableIconId() {
        // Returns Drawable tab icon for this page
        // TODO: make compatible
        return drawable_icon_id;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

}
