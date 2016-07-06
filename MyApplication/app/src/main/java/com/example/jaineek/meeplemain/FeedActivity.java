package com.example.jaineek.meeplemain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    MeepleFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;
    List<MeepleFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_feed);

        // Set up Tabs
        mActionBar = getSupportActionBar();


        // Support fragments require getSupportFragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // Create all Pages as Fragments
        LocalFeedFragment localFeedFragment = new LocalFeedFragment();
        MapFragment mapFragment = new MapFragment();

        // Add Fragments to List in order: LocalFeed, Map,
        mFragmentList = new ArrayList<>();
        mFragmentList.add(localFeedFragment);
        mFragmentList.add(mapFragment);


        fm.beginTransaction().add(R.id.viewPager_activity_feed, localFeedFragment,
                LocalFeedFragment.TAG_LOCAL_FEED).add(R.id.viewPager_activity_feed,
                mapFragment, MapFragment.TAG_MAP);

        mPagerAdapter = new MeepleFragmentPagerAdapter(fm);

        mViewPager.setAdapter(mPagerAdapter);
    }

    private void setActionBarTitle(String newTitle) {
        // Changes title of actionBar to newTitle
        mActionBar.setTitle(newTitle);
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
            MeepleFragment currentFragment = mFragmentList.get(i);
            setActionBarTitle(currentFragment.getTitle());
            return (Fragment) currentFragment;
        }

        @Override
        public int getCount() {
            // Returns number of pages in viewPager
            return mFragmentList.size();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
