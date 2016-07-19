package com.example.jaineek.meeplemain.model;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Krishnak97 on 7/8/2016.
 */

public class Post {
    // Post by user, displayed on LocalFeedFragment

    public String userUID;
    public String eventTitle;
    public String eventDesc;
    public Location location;
    public Date mEventDate;
    private String postMessage;

    public Post() {
        //Do nothing
    }
    public Post(String userUID, String eventTitle, String eventDesc, Date mEventDate, Location location) {
        this.userUID = userUID;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.mEventDate = mEventDate;
        this.location = location;
    }

    public void setPostMessage(String newPostMessage) {
        // Sets message of post
        postMessage = newPostMessage;
    }

    public String getPostMessage() {
        // Gets message of post
        return postMessage;
    }

}
