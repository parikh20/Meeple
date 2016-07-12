package com.example.jaineek.meeplemain.adapters_and_holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaineek.meeplemain.R;
import com.example.jaineek.meeplemain.model.Post;

import java.util.List;

/**
 * Created by Krishnak97 on 7/12/2016.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    // Adapter for RecyclerView and PostHolder
    List<Post> mDataList;
    Context mContext;

    public PostRecyclerAdapter(List<Post> localPostList, Context context) {
        // Constructor sets the list of Local Posts for RecyclerView
        mDataList = localPostList;
        mContext = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Called when new PostViewHolder is created
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        // Custom ViewHolder element with displayable info
        View customPostView = layoutInflater.inflate(R.layout.custom_view_post, parent, false);

        return new PostViewHolder(customPostView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder viewHolder, int position) {
        // Binds viewHolder's attributes to data
        Post postToBeBound = mDataList.get(position);
        viewHolder.bindViewsWithPost(postToBeBound);
    }

    @Override
    public int getItemCount() {
        // Returns num of local posts in data
        return mDataList.size();
    }
}