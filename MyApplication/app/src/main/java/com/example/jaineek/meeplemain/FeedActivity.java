package com.example.jaineek.meeplemain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FeedActivity extends FragmentActivity {
    MeepleFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Find mViewPager

        // Support fragments require getSupportFragmentManager
        mPagerAdapter = new MeepleFragmentPagerAdapter(
                getSupportFragmentManager());

    }

    private class MeepleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fragmentManager;

        public MeepleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int i) {
            // Returns Fragment at position i in FragmentManager
            return fragmentManager.getFragments().get(i);
        }

        @Override
        public int getCount() {
            // Returns number of pages in viewPager
            return fragmentManager.getFragments().size();
        }
    }
}
