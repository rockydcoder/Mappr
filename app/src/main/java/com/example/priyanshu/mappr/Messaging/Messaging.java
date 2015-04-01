package com.example.priyanshu.mappr.Messaging;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.priyanshu.mappr.R;

/**
 * Created by priyanshu-sekhar on 7/3/15.
 */



public class Messaging extends ListActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final String KEY_SPINNER_POS = "keySpinnerPos";

    private int mCurrSpinnerPos = 0;
    private SimpleCursorAdapter adapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        adapter = new SimpleCursorAdapter(activity,
                R.layout.main_list_item,
                null,
                new String[]{DataProvider.COL_NAME, DataProvider.COL_COUNT},
                new int[]{R.id.text1, R.id.text2},
                0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch (view.getId()) {
                    case R.id.text2:
                        int count = cursor.getInt(columnIndex);
                        if (count > 0) {
                            ((TextView) view).setText(String.format("%d new message%s", count, count == 1 ? "" : "s"));
                        }
                        return true;
                }
                return false;
            }
        });
        activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setListAdapter(adapter);
        //ActionBar actionBar = activity.getActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(Common.PROFILE_ID, String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_messaging, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddContactDialog dialog=AddContactDialog.newInstance();
                //dialog.show(getFragmentManager(),"AddContact");
                return true;

            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        android.content.CursorLoader loader = new android.content.CursorLoader(this,
                DataProvider.CONTENT_URI_PROFILE,
                new String[]{DataProvider.COL_ID, DataProvider.COL_NAME, DataProvider.COL_COUNT},
                null,
                null,
                DataProvider.COL_ID + " DESC");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // do not forget to clean up, if necessary
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SPINNER_POS, mCurrSpinnerPos);
    }

}
