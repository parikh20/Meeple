package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    MeepleFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;
    List<MeepleFragment> mFragmentList;
    TabLayout mTabLayout;

    private int mCurrentPage;
    final static String TAG = "FeedActivity";

    // Declaring Firebase variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Create Fragment pages, mFragmentList and add to FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        createAndAddFragments(fm);

        // Set up ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_feed);
        mPagerAdapter = new MeepleFragmentPagerAdapter(fm);
        mViewPager.setAdapter(mPagerAdapter);

        // Set up Tabs
        setupTabsandTitles();

        mActionBar = getSupportActionBar();
    }

    private void setupTabsandTitles() {
        // Sets up Tabs with Custom Views
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tab_layout);
//        mTabLayout.setupWithViewPager(mViewPager);   // must be set after setting Adapter
        final LayoutInflater localInflater = LayoutInflater.from(this);
        mTabLayout.post(new Runnable() {
            // Needed, otherwise tab titles wont work
            @Override
            public void run() {
                mTabLayout.setupWithViewPager(mViewPager);

                // Set custom View for each Tab
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = mTabLayout.getTabAt(i);
                    RelativeLayout customTabLayout =  (RelativeLayout) localInflater
                            .inflate(R.layout.custom_view_tab, mTabLayout, false);

                    TextView tabTextView = (TextView) customTabLayout.findViewById(R.id.tab_title);
                    tabTextView.setText(tab.getText());
                    tab.setCustomView(customTabLayout);
                }
            }
        });


    }

    private void createAndAddFragments(FragmentManager fm) {
        // Creates Fragment pages, mFragmentList and adds to FragmentManager

        LocalFeedFragment localFeedFragment = new LocalFeedFragment();
        MapFragment mapFragment = new MapFragment();

        // Add Fragments to List in order: LocalFeed, Map,
        mFragmentList = new ArrayList<>();
        mFragmentList.add(localFeedFragment);
        mFragmentList.add(mapFragment);

        // Add to FragmentManager
        fm.beginTransaction()
                .add(R.id.viewPager_activity_feed, localFeedFragment,
                        LocalFeedFragment.TAG_LOCAL_FEED)
                .add(R.id.viewPager_activity_feed,
                        mapFragment, MapFragment.TAG_MAP);
    }

    private void setActionBarTitle(String newTitle) {
        // Changes title of actionBar to newTitle
        mActionBar.setTitle(newTitle);
    }

    private class MeepleFragmentPagerAdapter extends FragmentStatePagerAdapter
            implements ViewPager.OnPageChangeListener{

        private FragmentManager fragmentManager;

        //FragmentStatePagerAdapter methods
        public MeepleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int i) {
            // Returns Fragment at position i in FragmentManager
            MeepleFragment currentFragment = mFragmentList.get(i);
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
            return currentFragment.getTitle();
        }

        //ViewPager.onPageChangeListener methods
        @Override
        public void onPageSelected(int position) {
            // Sets ActionBar title when Fragment page is selected
            MeepleFragment currentFragment = mFragmentList.get(position);
            setActionBarTitle(currentFragment.getTitle());
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Left blank
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Left blank
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
        // TODO: make intent for LoginActivity
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                // Log the user out
                logout();
                Log.d(TAG, "logout");
                return true;
            case R.id.settings:
                // Go to Settings Activity
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent toSettingsActivity = new Intent(FeedActivity.this, SettingsActivity.class);
                startActivity(toSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
