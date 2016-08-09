package com.example.jaineek.meeplemain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jaineek on 8/6/2016.
 */

public class Chat implements Parcelable {
    // Message sent from one user to another in ChatActivity

    public String sender;
    public String recipient;
    public String message;
    public Long timeStamp;

    public Chat() {
        // Empty Constructor for Firebase
    }

    public Chat(String sender, String recipient, String message, Long timeStamp) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.timeStamp = timeStamp;
    }

    public Chat(Parcel in) {
        sender = in.readString();
        recipient = in.readString();
        message = in.readString();
        timeStamp = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(recipient);
        dest.writeString(message);
        if (timeStamp == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(timeStamp);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Chat> CREATOR = new Parcelable.Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}
