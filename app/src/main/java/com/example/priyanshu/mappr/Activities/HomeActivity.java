package com.example.priyanshu.mappr.Activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.priyanshu.mappr.Adapters.MapprDatabaseAdapter;
import com.example.priyanshu.mappr.Fragments.NavigationDrawerFragment;
import com.example.priyanshu.mappr.Fragments.TimelineFragment;
import com.example.priyanshu.mappr.Messaging.GcmRegistrationAsyncTask;
import com.example.priyanshu.mappr.Messaging.Messaging;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.tabs.SlidingTabLayout;

import java.util.ArrayList;

public class HomeActivity extends ActionBarActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private static MapprDatabaseAdapter mapprDatabaseAdapter;

    private final int COUNT_OF_TABS = 1;
//    public ArrayList<String> groupTitles = new ArrayList<>();
//    public ArrayList<String> classMatesNames = new ArrayList<>();
    public ArrayList<Integer> postsIds = new ArrayList<>();
//    public ArrayList<String> teachersNames = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        mapprDatabaseAdapter = new MapprDatabaseAdapter(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
//            groupTitles = bundle.getStringArrayList("groupTitles");
//            classMatesNames = bundle.getStringArrayList("matesNames");
            postsIds = bundle.getIntegerArrayList(LoginPage.wallListTAG);
            for(Integer post:postsIds)
            mapprDatabaseAdapter.insertPost(post);
//            teachersNames = bundle.getStringArrayList("teachersNames");
        }


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
        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
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
            Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,Messaging.class);
            startActivity(intent);
            //new GcmRegistrationAsyncTask(this).execute();
            return true;
        }
        if(id==R.id.ibMail){
            Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
            new GcmRegistrationAsyncTask(this).execute();
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
                    return new TimelineFragment(postsIds);

//                case 1:
//                    return new GroupsFragment(groupTitles);
//                case 2:
//                    return new TeachersFragment(teachersNames);
//                case 3:
//                    return new StudentsFragment(classMatesNames);

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
