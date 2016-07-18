package com.example.jaineek.meeplemain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaineek.meeplemain.fragments.LocalFeedFragment;
import com.example.jaineek.meeplemain.fragments.MyMapFragment;
import com.example.jaineek.meeplemain.fragments.MeepleFragment;
import com.example.jaineek.meeplemain.fragments.MyPostsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private List<MeepleFragment> mFragmentList;
    private TabLayout mTabLayout;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public static final String TAG = "FeedActivity";

    // Tags for all Intent extras
    public static final String KEY_EXTRA_LOCATION = "com.example.jaineek.meeple.extra_tag_location";

    // Firebase variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Location variables
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check for dark theme
        mSharedPreferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        if (mSharedPreferences.getBoolean("key_change_theme", false)) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mContext = FeedActivity.this;

        // Used for location services
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(FeedActivity.this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Create Fragment pages, mFragmentList, add to FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        createAndAddFragments(fm);

        // Set up ViewPager & Adapter
        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_feed);
        MeepleFragmentPagerAdapter pagerAdapter = new MeepleFragmentPagerAdapter(fm);
        mViewPager.setAdapter(pagerAdapter);

        setupViewPagerListener();

        setupTabsAndTitles();
    }

    private void setupViewPagerListener() {
        // Sets up listener for ViewPager to change ActionBar title
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // Left blank
            }

            @Override
            public void onPageSelected(int position) {
                // Changes ActionBar title to Fragment title at position
                setActionBarTitle(mFragmentList.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Left blank
            }
        });

        // Setup first page title
        setActionBarTitle(mFragmentList.get(0).getTitle());
    }

    private void setupTabsAndTitles() {
        // Sets up Tabs with Custom Pages and ViewPager

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

                    // Set icon for tab number i
                    int SDK_VERSION_FOR_ICONS = Build.VERSION_CODES.LOLLIPOP;

                    if (Build.VERSION.SDK_INT >= SDK_VERSION_FOR_ICONS) {
                        // Use tab icons if user has Lollipop or higher
                        Drawable icon = getDrawable(getTabDrawableId(i));
                        tab.setIcon(icon);
                    } else {
                        // Just use text titles
                        TextView tabTextView = (TextView) customTabLayout
                                .findViewById(R.id.tab_title);
                        tabTextView.setText(tab.getText());
                    }
                }
            }
        });
    }

    private void createAndAddFragments(FragmentManager fm) {
        // Creates Fragment pages, mFragmentList and adds to FragmentManager

        // Add Fragments to List in order: LocalFeed, MyPosts, Map
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new LocalFeedFragment());
        mFragmentList.add(new MyPostsFragment());
        mFragmentList.add(new MyMapFragment());

        // Add to FragmentManager
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        for (MeepleFragment fragment : mFragmentList) {
            fragmentTransaction.add(R.id.viewPager_activity_feed, (Fragment) fragment,
                        fragment.getFragmentTag());
        }
    }

    private int getTabDrawableId(int tabNumber) {
        // Returns the Drawable icon ID for tab at tabNumber
        MeepleFragment fragment = mFragmentList.get(tabNumber);
        return fragment.getDrawableIconId();
    }

    private void setActionBarTitle(String newTitle) {
        // Changes title of actionBar to newTitle
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(newTitle);
    }

    private class MeepleFragmentPagerAdapter extends FragmentStatePagerAdapter {
//            implements ViewPager.OnPageChangeListener{

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

//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Returns Fragment title at position
//            MeepleFragment currentFragment = mFragmentList.get(position);
//            return currentFragment.getTitle();
//        }
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
                Intent toSettingsActivity = new Intent(FeedActivity.this, SettingsActivity.class);
                startActivity(toSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* GOOGLE MAPS METHODS */

    public Location getmLastLocation() {
        // Returns the last known location of the user
        return mLastLocation;
    }

    @Override
    protected void onStart() {
        // Connect to location services
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnect to location services
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation != null) {
                // Use the last location in some way
                mLastLocation = lastLocation;
            }
        } catch (SecurityException e) {
            // Indicate that location services are not allowed at this time
            Toast.makeText(FeedActivity.this, getString(R.string.error_location_not_supported),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int connectionStuff) {
        // Left blank
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionStuff) {
        // Left blank
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
