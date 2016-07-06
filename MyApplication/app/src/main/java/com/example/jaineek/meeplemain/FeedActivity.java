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

        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_feed);

        // Support fragments require getSupportFragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // Create all Fragments
        LocalFeedFragment localFeedFragment = new LocalFeedFragment();
        MapFragment mapFragment = new MapFragment();

        // Add Fragments to fm in order: LocalFeed, Map,
        fm.beginTransaction().add(R.id.viewPager_activity_feed, localFeedFragment)
                .add(R.id.viewPager_activity_feed, mapFragment);

        mPagerAdapter = new MeepleFragmentPagerAdapter(fm);

        mViewPager.setAdapter(mPagerAdapter);

    }

    private class MeepleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        // TODO: CHANGE ALL OF THIS SHIT

        private FragmentManager fragmentManager;

        public MeepleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int i) {
            // Returns Fragment at position i in FragmentManager
            return new MapFragment();
        }

        @Override
        public int getCount() {
            // Returns number of pages in viewPager
            return 2;
        }
    }
}
