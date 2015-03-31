package com.example.priyanshu.mappr.Data;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocky on 5/3/15.
 */
public class MapprApplication extends Application {
    private static MapprApplication sInstance;

    public static String[] email_arr;
    private static SharedPreferences prefs;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        List<String> emailList=getEmailList();
        email_arr = emailList.toArray(new String[emailList.size()]);
    }

    private List<String> getEmailList() {
        List<String> lst = new ArrayList<String>();
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                lst.add(account.name);
            }
        }
        return lst;
    }

    public static MapprApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
