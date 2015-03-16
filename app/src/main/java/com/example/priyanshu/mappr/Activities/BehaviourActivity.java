package com.example.priyanshu.mappr.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.example.priyanshu.mappr.Data.Badge;
import com.example.priyanshu.mappr.Data.BadgesEarned;
import com.example.priyanshu.mappr.Fragments.BadgesEarnedFragment;
import com.example.priyanshu.mappr.Fragments.NavigationDrawerFragment;
import com.example.priyanshu.mappr.Fragments.PerfAnalFragment;
import com.example.priyanshu.mappr.Fragments.RecentBadgesFragment;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;
import com.example.priyanshu.mappr.tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Date;

import static com.example.priyanshu.mappr.Activities.LoginPage.badges;
import static com.example.priyanshu.mappr.Activities.LoginPage.recentBadges;

/**
 * Created by priyanshu-sekhar on 2/3/15.
 */
public class BehaviourActivity extends ActionBarActivity{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private final int COUNT_OF_TABS = 3;
    private ArrayList<Integer> badgesIds = new ArrayList<>();
    private ArrayList<Badge> recentBadgesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);
        extractBadgeIds(badges);
        extractBadgeInfo(recentBadges);

        // Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView user=(ImageView)findViewById(R.id.loggedUser);
        user.setImageResource(R.drawable.student);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pagerAcads);

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabsAcads);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab, R.id.tv_tab_title);
        mTabs.setBackgroundColor(getResources().getColor(R.color.primary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.tab_selected_indicator));
        mTabs.setViewPager(mPager);
    }

    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home_page, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void extractBadgeIds(String string) {
        String[] arr = string.split(",");
        for(String s: arr) {
            badgesIds.add(Integer.parseInt(s));
        }

    }

    private void extractBadgeInfo(String string) {
        String[] arr = string.split(",");
        String[] badgeTitles = getResources().getStringArray(R.array.badges_list);
        for(int i = 0; i < arr.length; i+=3) {
            Badge currentBadge = new Badge();
            currentBadge.setBadgeId(Integer.parseInt(arr[i]));
            currentBadge.setTeacherId(Integer.parseInt(arr[i+1]));
            currentBadge.setTimestamp(new Date(Long.parseLong(arr[i+2]) * 1000));
            currentBadge.setPositiveBadge(currentBadge.getBadgeId() <= 6);
            currentBadge.setBadgeTitle(badgeTitles[currentBadge.getBadgeId() - 1]);
            currentBadge.setTeacher("Soham Chokshi");
            recentBadgesList.add(currentBadge);

        }
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] tabsTitle;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabsTitle = getResources().getStringArray(R.array.behaviour_fragments_list);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new BadgesEarnedFragment(badgesIds);
                case 1:
                    return new RecentBadgesFragment(recentBadgesList);
                case 2:
                    return new PerfAnalFragment();
            }

            return null;
        }
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return COUNT_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }
    }



}
