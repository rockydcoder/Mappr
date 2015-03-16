package com.example.priyanshu.mappr.Activities;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.priyanshu.mappr.Fragments.CompChartFragment;
import com.example.priyanshu.mappr.Fragments.NavigationDrawerFragment;
import com.example.priyanshu.mappr.Fragments.RecentEndorseFragment;
import com.example.priyanshu.mappr.Fragments.SkillEndorseFragment;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.tabs.SlidingTabLayout;


public class TrendsActivity extends ActionBarActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private final int COUNT_OF_TABS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

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

        // Set up the tabs
        mPager = (ViewPager) findViewById(R.id.trends_pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.trends_tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab, R.id.tv_tab_title);
        mTabs.setBackgroundColor(getResources().getColor(R.color.primary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.tab_selected_indicator));
        mTabs.setViewPager(mPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trends, menu);
        return true;
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

    class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] tabsTitle;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
            tabsTitle = getResources().getStringArray(R.array.trends_fragments_list);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SkillEndorseFragment();
                case 1:
                    return new RecentEndorseFragment();
                case 2:
                    return new CompChartFragment();

            }

            return null;
        }

        @Override
        public int getCount() {
            return COUNT_OF_TABS;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }
    }
}
