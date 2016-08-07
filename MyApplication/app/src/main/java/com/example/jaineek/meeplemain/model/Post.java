package com.example.jaineek.meeplemain.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Krishnak97 on 7/8/2016.
 */

public class Post implements Parcelable {
    // Post by user, displayed on LocalFeedFragment

    public String userUID;
    public String eventTitle;
    public String eventDesc;
    public MeepleLocation eventLocation;
    public Date eventDate;
    public String comments;
    public Long timestamp;

    public static final String userUID_KEY = "userUID";

    public Post() {
        //Do nothing
    }

    public Post(String userUID, String eventTitle, String eventDesc, Date eventDate, MeepleLocation eventLocation) {
        this.userUID = userUID;
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventDate  = eventDate;
        this.eventLocation = eventLocation;
        this.comments = "DUMMY VALUE";
        this.timestamp = eventDate.getTime();
    }

    /* PARCELABLE METHODS */

    protected Post(Parcel in) {
        userUID = in.readString();
        eventTitle = in.readString();
        eventDesc = in.readString();
        eventLocation = (MeepleLocation) in.readValue(MeepleLocation.class.getClassLoader());
        long tmpEventDate = in.readLong();
        eventDate = tmpEventDate != -1 ? new Date(tmpEventDate) : null;
        comments = in.readString();
        timestamp = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userUID);
        dest.writeString(eventTitle);
        dest.writeString(eventDesc);
        dest.writeValue(eventLocation);
        dest.writeLong(eventDate != null ? eventDate.getTime() : -1L);
        dest.writeString(comments);
        dest.writeLong(timestamp);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}