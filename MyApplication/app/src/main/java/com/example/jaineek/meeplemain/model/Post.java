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
    public MeepleLocation eventLocation;
    public Date eventDate;

//    private String postMessage;

    public Post() {
        //Do nothing
    }
    public Post(String userUID, String eventTitle, String eventDesc, Date eventDate, MeepleLocation eventLocation) {
        this.userUID = userUID;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventDate  = eventDate;
        this.eventLocation = eventLocation;
    }

//    public void setPostMessage(String newPostMessage) {
//        // Sets message of post
//        postMessage = newPostMessage;
//    }
//
//    public String getPostMessage() {
//        // Gets message of post
//        return postMessage;
//    }

}
