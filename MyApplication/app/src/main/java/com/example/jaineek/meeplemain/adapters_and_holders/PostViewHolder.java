package com.example.jaineek.meeplemain.adapters_and_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Post;

/**
 * Created by Krishnak97 on 7/12/2016.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
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
