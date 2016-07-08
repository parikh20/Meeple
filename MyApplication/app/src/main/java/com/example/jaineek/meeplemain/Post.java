package com.example.jaineek.meeplemain;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

/**
 * Created by Krishnak97 on 7/8/2016.
 */

public class Post {
    // Post by user, displayed on LocalFeedFragment

    private FirebaseUser user;
    private Location location;
    private String postMessage;

    public void setPostMessage(String newPostMessage) {
        // Sets message of post
        postMessage = newPostMessage;
    }

    public String getPostMessage() {
        // Gets message of post
        return postMessage;
    }

}
