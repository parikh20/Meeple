package com.example.jaineek.meeplemain.adapters_and_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Post;

import java.text.SimpleDateFormat;

/**
 * Created by Krishnak97 on 7/12/2016.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    // ViewHolder for Posts in LocalFeedFragment's RecyclerView

    // TODO: add more member variables
    private Post mPost;
    private TextView mPostTitle;
    private TextView mPostDate;
    private TextView mPostLocation;
    private TextView mPostDescription;

    private SimpleDateFormat mSimpleDateFormat;

    public PostViewHolder(View itemView) {
        super(itemView);
        // ViewHolder now holds custom Post view (itemView)

        // Find Views within itemView
        // TODO: actually assign member variables

        mPostDescription =(TextView) itemView.findViewById(R.id.new_post_description_textView);
        mPostTitle =(TextView) itemView.findViewById(R.id.new_post_title_textView);
        mPostLocation = (TextView) itemView.findViewById(R.id.new_post_location_textView);
        mPostDate = (TextView) itemView.findViewById(R.id.new_post_date_textView);

        mSimpleDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy  hh:mm aaa");
    }

    public void bindViewsWithPost(Post post) {
        // Update member variables with info from Post
        // TODO: actually do this
        mPost = post;

        mPostTitle.setText(mPost.eventTitle);
        if (mPost.eventDate != null) {
            mPostDate.setText(mSimpleDateFormat.format(mPost.eventDate));
        }
        if (mPost.eventLocation != null) {
            mPostLocation.setText(mPost.eventLocation.toString());
        }
        if (mPost.eventDesc != null) {
            mPostDescription.setText(mPost.eventDesc);
        }
    }
}
