package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    MeepleFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;
    List<MeepleFragment> mFragmentList;
    PagerTabStrip mPagerTabStrip;
    final static String TAG = "FeedActivity";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_feed);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip_toolbar);

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

        @Override
        public CharSequence getPageTitle(int position) {
            // Returns Fragment title at position
            MeepleFragment currentFragment = mFragmentList.get(position);
            setActionBarTitle(currentFragment.getTitle());
            return currentFragment.getTitle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
}

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                Log.d(TAG, "logout");
                return true;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent toSettingsActivity = new Intent(FeedActivity.this, SettingsActivity.class);
                startActivity(toSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
