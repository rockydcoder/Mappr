package com.example.priyanshu.mappr.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.priyanshu.mappr.Activities.util.SystemUiHider;
import com.example.priyanshu.mappr.Adapters.MapprDatabaseAdapter;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.priyanshu.mappr.Extras.Keys.Database.KEY_USER_IDS;
import static com.example.priyanshu.mappr.Extras.Keys.Database.KEY_USER_NAMES;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_AMPERSAND;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_EQUAL;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_QUESTION;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_LOG_IN;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_REQUEST_TYPE;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_TYPE_ALL;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_USERTYPE;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Splash extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    final RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
    private Context context = this;
    private static MapprDatabaseAdapter mapprDatabaseAdapter;
    private ArrayList<Integer> studentUserId = new ArrayList<>();
    private ArrayList<String> studentName = new ArrayList<>();
    private ArrayList<Integer> teacherUserId = new ArrayList<>();
    private ArrayList<String> teacherName = new ArrayList<>();
    private ArrayList<Integer> parentUserId = new ArrayList<>();
    private ArrayList<String> parentName = new ArrayList<>();
    private Animation blink;


    private final static String SHARED_PREF_TAG = "IS FIRST TIME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mapprDatabaseAdapter = new MapprDatabaseAdapter(context);

        ImageView mappr=(ImageView)findViewById(R.id.mappr_logo);
        blink= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        mappr.startAnimation(blink);
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.mappr_logo);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(SHARED_PREF_TAG, true)) {

            JsonObjectRequest studentRequest = new JsonObjectRequest(Request.Method.GET,
                    getRequestUrl("student"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String userid = response.getString(KEY_USER_IDS);
                                String usernames = response.getString(KEY_USER_NAMES);

                                studentUserId = extractUserIds(userid);
                                studentName = extractUserNames(usernames);

                                for(int i = 0; i < studentUserId.size(); i++) {
                                    long id = mapprDatabaseAdapter.insertStudent(studentName.get(i), studentUserId.get(i));
                                    Log.d("Splash - Student", id+"");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            requestQueue.add(studentRequest);

            JsonObjectRequest teacherRequest = new JsonObjectRequest(Request.Method.GET,
                    getRequestUrl("teacher"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String userid = response.getString(KEY_USER_IDS);
                                String usernames = response.getString(KEY_USER_NAMES);

                                teacherUserId = extractUserIds(userid);
                                teacherName = extractUserNames(usernames);

                                for(int i = 0; i < teacherUserId.size(); i++) {
                                    long id = mapprDatabaseAdapter.insertTeacher(teacherName.get(i), teacherUserId.get(i));
                                    Log.d("Splash - Teacher", id+"");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(teacherRequest);

            JsonObjectRequest parentRequest = new JsonObjectRequest(Request.Method.GET,
                    getRequestUrl("parent"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String userid = response.getString(KEY_USER_IDS);
                                String usernames = response.getString(KEY_USER_NAMES);

                                parentUserId = extractUserIds(userid);
                                parentName = extractUserNames(usernames);

                                for(int i = 0; i < parentUserId.size(); i++) {
                                    long id = mapprDatabaseAdapter.insertParent(parentName.get(i), parentUserId.get(i));
                                    Log.d("Splash - Parent", id+"");
                                }

                                Toast.makeText(context, "Welcome to Mappr!!!", Toast.LENGTH_LONG).show();
                                changeActivity();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(parentRequest);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SHARED_PREF_TAG, false);
            editor.commit();

            changeActivity();

        }

        else {
            Log.d("Splash","change");

            changeActivity();
        }



    }

    private void changeActivity() {
        Intent intent = new Intent(Splash.this, LoginPage.class);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private String getRequestUrl(String type) {
        return URL_LOG_IN +
                URL_CHAR_QUESTION +
                URL_REQUEST_TYPE +
                URL_CHAR_EQUAL +
                URL_TYPE_ALL +
                URL_CHAR_AMPERSAND +
                URL_USERTYPE +
                URL_CHAR_EQUAL +
                type;
    }

    private ArrayList<Integer> extractUserIds(String userids) {
        String[] str = userids.split(",");
        ArrayList<Integer> userIds = new ArrayList<>();
        for(String s: str) {
            userIds.add(Integer.parseInt(s));
        }

        return userIds;
    }

    private ArrayList<String> extractUserNames(String usernames) {
        String[] str = usernames.split(",");
        ArrayList<String> userIds = new ArrayList<>();
        for(String s: str) {
            userIds.add(s);
//            Log.d("Splash", s);
        }

        return userIds;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
