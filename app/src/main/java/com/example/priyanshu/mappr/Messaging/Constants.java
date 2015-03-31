package com.example.priyanshu.mappr.Messaging;

/**
 * Created by priyanshu-sekhar on 7/3/15.
 */
public interface Constants {


    String PROJECT_NUMBER="235765685035";

    String KEY_STATE = "keyState";
    String KEY_REG_ID = "keyRegId";
    String KEY_MSG_ID = "keyMsgId";
    String KEY_ACCOUNT = "keyAccount";
    String KEY_MESSAGE_TXT = "keyMessageTxt";
    String KEY_EVENT_TYPE = "keyEventbusType";
    String ACTION = "action";
    // very simply notification handling :-)
    int NOTIFICATION_NR = 10;

    long GCM_DEFAULT_TTL = 2 * 24 * 60 * 60 * 1000; // two days


    String PACKAGE = "com.grokkingandroid.sampleapp.samples.gcm";
    // actions for server interaction
    String ACTION_REGISTER = PACKAGE + ".REGISTER";
    String ACTION_UNREGISTER = PACKAGE + ".UNREGISTER";
    String ACTION_ECHO = PACKAGE + ".ECHO";

    // action for notification intent
    String NOTIFICATION_ACTION = PACKAGE + ".NOTIFICATION";

    String DEFAULT_USER = "fakeUser";
    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */

    String SERVER_URL = "http://optimal-carving-87709.appspot.com/";

    /**
     * Google API project id registered to use GCM.
     */
    String SENDER_ID = "optimal-carving-87709";

    enum EventbusMessageType {
        REGISTRATION_FAILED, REGISTRATION_SUCCEEDED, UNREGISTRATION_SUCCEEDED, UNREGISTRATION_FAILED;
    }

    enum State {
        REGISTERED, UNREGISTERED;
    }



}