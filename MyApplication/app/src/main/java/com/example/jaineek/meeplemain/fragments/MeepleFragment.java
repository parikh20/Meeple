package com.example.jaineek.meeplemain.fragments;

import android.graphics.drawable.Drawable;

/**
 * Created by Krishnak97 on 7/6/2016.
 */

public interface MeepleFragment {

    /* Necessary variables :
        public static final String TAG;
        public static String title_blank_fragment;
        public static int drawable_icon_id;
    */

    String getTitle();

    String getFragmentTag();

    int getDrawableIconId();
}
