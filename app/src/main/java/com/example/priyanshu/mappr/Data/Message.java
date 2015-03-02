package com.example.priyanshu.mappr.Data;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rocky on 27/2/15.
 */
public class Message {

    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
