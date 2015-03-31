package com.example.priyanshu.mappr.Messaging;

/**
 * Created by priyanshu-sekhar on 14/3/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.Random;

public class GcmUtil {

    private static final String TAG = "GcmUtil";

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    private GoogleCloudMessaging gcm;
    private Context context;
    private String regId;

    private AsyncTask registrationTask;

    /**
     * @param context
     */
    public GcmUtil(Context context) {
        this.context = context;
        registerInBackground();
    }

//    public boolean register(Activity activity) {
//        Log.e("register", "register");
//        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
//        if (checkPlayServices(activity)) {
//            gcm = GoogleCloudMessaging.getInstance(context);
//            regId = getRegistrationId(context);
//
//            if (TextUtils.isEmpty(regId)) {
//                if (registrationTask == null)
//                    if (activity instanceof GcmListener) registerInBackground((GcmListener)activity);
//                    else registerInBackground(null);
//            } else {
//                Log.i(TAG, regId);
//                return true;
//            }
//        } else {
//            Log.i(TAG, "No valid Google Play Services APK found.");
//        }
//        return false;
//    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (TextUtils.isEmpty(registrationId)) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        registrationTask = new AsyncTask<Void, Void, Boolean>() {
        String msg="";
            @Override
            protected Boolean doInBackground(Void... params) {
                long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
                for (int i = 1; i <= MAX_ATTEMPTS; i++) {
                    Log.d(TAG, "Attempt #" + i + " to register");
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(context);
                        }
                        regId = gcm.register(Common.getSenderId());
                        msg = "Device registered, registration ID=" + regId;

                        // You should send the registration ID to your server over HTTP,
                        // so it can use GCM/HTTP or CCS to send messages to your app.
                        String email="pri.patra@gmail.com";
                        ServerUtilities.register(email, regId);

                        // Save the regId - no need to register again.
                        storeRegistrationId(context, regId);
                        return Boolean.TRUE;

                    } catch (IOException ex) {
                        Log.e(TAG, "Failed to register on attempt " + i + ":" + ex);
                        msg = "Error: " + ex.getMessage();
                        if (i == MAX_ATTEMPTS) {
                            break;
                        }
                        try {
                            Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                            Thread.sleep(backoff);
                        } catch (InterruptedException e1) {
                            // Activity finished before we complete - exit.
                            Log.d(TAG, "Thread interrupted: abort remaining retries!");
                            Thread.currentThread().interrupt();
                        }
                        // increase backoff exponentially
                        backoff *= 2;
                    }
                }
                return Boolean.FALSE;
            }

            @Override
            protected void onPostExecute(Boolean status) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                //broadcastStatus(status);
            }
        }.execute(null, null, null);

    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(context.getPackageName()+".GCM", Context.MODE_PRIVATE);
    }

    public void cleanup() {
        if (registrationTask != null) {
            registrationTask.cancel(true);
        }
        if (gcm != null) {
            gcm.close();
        }
    }
}