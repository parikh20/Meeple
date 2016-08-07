package com.example.jaineek.meeplemain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jaineek on 8/6/2016.
 */

public class Chat implements Parcelable{
    String sender;
    String recipient;
    String message;
    Long timeStamp;

    public Chat(String sender, String recipient, String message, Long timeStamp) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.timeStamp = timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
