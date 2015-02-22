package com.example.priyanshu.mappr;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.example.priyanshu.mappr.tabs.SlidingTabLayout;


public class HomePage extends ActionBarActivity {
//        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;



    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private final int COUNT_OF_TABS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab, R.id.tv_tab_title);
        mTabs.setBackgroundColor(getResources().getColor(R.color.tab_bg));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.tab_selected_indicator));
        mTabs.setViewPager(mPager);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home_page, menu);
//            restoreActionBar();
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

    class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] tabsTitle;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabsTitle = getResources().getStringArray(R.array.home_fragments_list);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new MyFragment();
                case 1:
                    return new GroupsFragment();
                case 2:
                    return new TeachersFragment();
                case 3:
                    return new StudentsFragment();
            }

            return null;
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

    public static class MyFragment extends Fragment {


        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_text, container, false);

            return layout;
        }
    }


}
