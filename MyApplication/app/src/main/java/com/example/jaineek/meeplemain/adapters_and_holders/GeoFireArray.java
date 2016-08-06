/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jaineek.meeplemain.adapters_and_holders;

import android.util.Log;

import com.example.jaineek.meeplemain.FeedActivity;
import com.example.jaineek.meeplemain.model.Post;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class implements an array-like collection on top of a Firebase location, using the developer's
 * GeoFire location.
 */
class GeoFireArray<T> implements GeoQueryEventListener, ValueEventListener {

    public static final String LOG_TAG = "GeoFireArray";
    public interface OnChangedListener {
        enum EventType { Added, Changed, Removed, Moved }
        void onChanged(EventType type, int index, int oldIndex);
    }

    private GeoQuery mGeoQuery;
    private OnChangedListener mListener;
    private ArrayList<DataSnapshot> mSnapshots;


    private DatabaseReference mFirebaseModelRef;
    private Class<T> mModelClass;

    /** @param geoQuery This is the location query which will popular the GeoFireArray with instances
     *                  of the modelClass.
     *  @param firebaseModelRef This is the Firebase DatabaseReference at which all the modelClass
     *                                instances are stored. It is used to set Firebase
     *                                ValueEventListeners on the modelClass.
     *  @param modelClass This is the type of Java object stored at firebaseModelReference (in the
     *                    developer's Firebase Database.
     */
    public GeoFireArray(GeoQuery geoQuery, DatabaseReference firebaseModelRef, Class<T> modelClass) {

        mGeoQuery = geoQuery;
        mSnapshots = new ArrayList<DataSnapshot>();
        mModelClass = modelClass;
        mFirebaseModelRef = firebaseModelRef;

        // Add a GeoFire listener to the GeoQuery
        mGeoQuery.addGeoQueryEventListener(this);
    }

    public void cleanup() {
        mGeoQuery.removeGeoQueryEventListener(this);
    }

    public int getCount() {
        return mSnapshots.size();
    }

    public DataSnapshot getItem(int index) {
        return mSnapshots.get(index);
    }

    private int getIndexForKey(String key) {
        int index = 0;
        for (DataSnapshot snapshot : mSnapshots) {
            if (snapshot.getKey().equals(key)) {
                return index;
            } else {
                index++;
            }
        }
        throw new IllegalArgumentException("Key not found");
    }

    /* GeoQueryEventListener methods */

    @Override
    public void onKeyEntered(String key, GeoLocation geoLocation) {

        // Add Firebase listeners to the specific child of the Firebase DatabaseReference
        mFirebaseModelRef.child(key).addValueEventListener(this);
    }

    @Override
    public void onKeyExited(String key) {
        Log.d(LOG_TAG, "deal " + key + " is no longer in the search area");
        // Remove Firebase listeners
        mFirebaseModelRef.child(key).removeEventListener(this);
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        Log.d(LOG_TAG, String.format("Key " + key + " moved within the search area to [%f,%f]", location.latitude, location.longitude));
    }

    @Override
    public void onGeoQueryReady() {
        Log.d(LOG_TAG, "All initial data has been loaded and events have been fired!");
    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Log.e(LOG_TAG, "There was an error with this query: " + error);
    }
    // End of GeoQueryEventListeners

    // Start of ValueEventListener methods

    // TODO: make this generic
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get Model object from the dataSnapshot
        T model = (T) dataSnapshot.getValue(mModelClass);
        Post post = (Post) model;

        // Get current date at midnight
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); //Set to midnight
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date currentDate = c.getTime();

        // Check if time has not passed
        if (model != null && post.eventDate.compareTo(currentDate) >= 0)  {
            mSnapshots.add(dataSnapshot);
            notifyChangedListeners(OnChangedListener.EventType.Added, mSnapshots.size() - 1);
        }
    }

    public void onCancelled(DatabaseError firebaseError) {
        // TODO: what do we do with this?
        Log.e(LOG_TAG, "There was an error within a ValueEventListener");
    }
    // End of ValueEventListener methods


    public void setOnChangedListener(OnChangedListener listener) {
        mListener = listener;
    }

    protected void notifyChangedListeners(OnChangedListener.EventType type, int index) {
        notifyChangedListeners(type, index, -1);
    }
    protected void notifyChangedListeners(OnChangedListener.EventType type, int index, int oldIndex) {
        if (mListener != null) {
            mListener.onChanged(type, index, oldIndex);
        }
    }
}
