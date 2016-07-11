package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Krishnak97 on 7/5/2016.
 */

public class LocalFeedFragment extends Fragment implements MeepleFragment{

    public static final String TAG_LOCAL_FEED = "FRAGMENT_LOCAL_FEED";
    public static String title_local_feed_fragment;

    private RecyclerView mLocalFeedRecyclerView;
    private FloatingActionButton mNewPostFAB;
    private List<Post> mLocalPosts = new ArrayList<>();

    // Declaring Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title_local_feed_fragment = getString(
                R.string.title_local_feed_fragment);
        // Save all instance information
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_local_feed, container, false);

//        // Declare all mVariables
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mLocalFeedRecyclerView = (RecyclerView) v.findViewById(R.id.local_feed_recyclerView);
        mLocalPosts = findLocalPosts();

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

        // Creates adapter w/ data. Sets up w/ RecyclerView
        LocalFeedAdapter localFeedAdapter = new LocalFeedAdapter(mLocalPosts);
        mLocalFeedRecyclerView.setAdapter(localFeedAdapter);
    }

    private void setUpFloatingActionButton() {
        // Wires the NewPost floating action button

        mNewPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When clicked, start NewPostActivity
                // TODO: create intent (both finish and not) function
                Intent toNewPostActivity = new Intent(getActivity(), NewPostActivity.class);
                startActivity(toNewPostActivity);
            }
        });
    }
//
    private List<Post> findLocalPosts() {
        // Returns List<Post> close to user locaton
        // TODO: implement actual GPS tracking
        List<Post> testPosts = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            Post post = new Post();
            post.setPostMessage("Post " + i);
            testPosts.add(post);
        }

        return testPosts;
    }


    @Override
    public String getTitle() {
        // returns Title of fragment
        return title_local_feed_fragment;
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder for Posts in LocalFeedFragment's RecyclerView

        // TODO: add more member variables
        private Post mPost;
        private TextView mPostMessage;

        public PostViewHolder(View itemView) {
            super(itemView);
            // ViewHolder now holds custom Post view (itemView)

            // Find Views within itemView
            // TODO: actually assign member variables

            mPostMessage =(TextView) itemView.findViewById(R.id.post_message_textView);

        }

        public void bindViewsWithPost(Post post) {
            // Update member variables with info from Post
            // TODO: actually do this
            mPost = post;
            mPostMessage.setText(mPost.getPostMessage());

        }
    }
//
    private class LocalFeedAdapter extends RecyclerView.Adapter<PostViewHolder> {
        // Adapter for RecyclerView and PostHolder
        List<Post> mLocalPostList;

        public LocalFeedAdapter(List<Post> localPostList) {
            // Constructor sets the list of Local Posts for RecyclerView
            mLocalPostList = localPostList;
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Called when new PostViewHolder is created
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            // Custom ViewHolder element with displayable info
            View customPostView = layoutInflater.inflate(R.layout.custom_view_post, parent, false);

            return new PostViewHolder(customPostView);
        }

        @Override
        public void onBindViewHolder(PostViewHolder viewHolder, int position) {
            // Binds viewHolder's attributes to data
            Post postToBeBound = mLocalPostList.get(position);
            viewHolder.bindViewsWithPost(postToBeBound);
        }

        @Override
        public int getItemCount() {
            // Returns num of local posts in data
            return mLocalPostList.size();
        }
    }
}
