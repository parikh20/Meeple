package com.example.jaineek.meeplemain.fragments;

import android.content.Intent;
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
import com.example.jaineek.meeplemain.adapters_and_holders.FirebaseRecyclerAdapter;
import com.example.jaineek.meeplemain.adapters_and_holders.PostRecyclerAdapter;
import com.example.jaineek.meeplemain.adapters_and_holders.PostViewHolder;
import com.example.jaineek.meeplemain.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishnak97 on 7/12/2016.
 */

public class MyPostsFragment extends Fragment implements MeepleFragment {

    public static final String TAG = "FRAGMENT_LOCAL_FEED";
    public static String title_local_feed_fragment = "My Posts";
    public static int drawable_icon_id = R.drawable.ic_message_white_48dp;

    private RecyclerView mMyPostsRecyclerView;
    private FloatingActionButton mNewPostFAB;
    private List<Post> mMyPosts = new ArrayList<>();

    // Declaring Firebase variables
    private DatabaseReference mPostsReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Save all instance information
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_posts, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        mPostsReference = FirebaseDatabase.getInstance()
                .getReference(FeedActivity.PATH_TO_POSTS);
        mMyPostsRecyclerView = (RecyclerView) v.findViewById(R.id.my_posts_recyclerView);
        mMyPosts = findMyPosts();

        setUpRecyclerViewAndAdapter();

        mNewPostFAB = (FloatingActionButton) v.findViewById(R.id.fab_new_post);
        setUpFloatingActionButton();

        return v;
    }

    private void setUpRecyclerViewAndAdapter() {
        // Sets up RecyclerView, FirebaseRecyclerAdapter with data and LayoutManager

        // Sets linearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mMyPostsRecyclerView.setLayoutManager(layoutManager);

        // Get only posts for this userUID
        Query postsForThisUser =  mPostsReference.orderByChild(Post.userUID_KEY)
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Creates adapter w/ data. Sets up w/ RecyclerView
        FirebaseRecyclerAdapter<Post, PostViewHolder> localFeedAdapter =
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.custom_view_post,
                        PostViewHolder.class, postsForThisUser) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                        // Populate viewHolder with Post info
                        viewHolder.bindViewsWithPost(model);
                    }
                };
        mMyPostsRecyclerView.setAdapter(localFeedAdapter);
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

    private List<Post> findMyPosts() {
        // Returns List<Post> close to user locaton
        // TODO: implement actual finding my post
        List<Post> testPosts = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            Post post = new Post();
            post.eventDesc = "My Post " + i;
            testPosts.add(post);
        }
        return testPosts;
    }

    /* MEEPLE FRAGMENT METHODS */

    @Override
    public String getTitle() {
        // returns Title of fragment
        return title_local_feed_fragment;
    }

    @Override
    public int getDrawableIconId() {
        // Returns Drawable tab icon for this page
        return drawable_icon_id;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
