package com.example.priyanshu.mappr.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import static com.example.priyanshu.mappr.Extras.URLEndPoints.*;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.example.priyanshu.mappr.Extras.Keys.LogIn.*;


public class LoginPage extends ActionBarActivity implements View.OnClickListener {

    private EditText userName;
    private EditText passWord;
    private CheckBox rememberPassword;
    private Button login, signup;
    private Spinner sLogIn;
    private String username = null;
    private String password = null;
    public static String name;
    public static Bitmap dispPic;
    public static int id;
    public static ArrayList<Integer> badgesList = new ArrayList<>();
    public static String badges;
    public static String recentBadges;
//    public ArrayList<Integer> groupsList = new ArrayList<>();
    public ArrayList<Integer> wallList = new ArrayList<>();
//    public ArrayList<Integer> classmatesList = new ArrayList<>();
//    public ArrayList<Integer> teachersList = new ArrayList<>();
//    public ArrayList<String> groupsTitles = new ArrayList<>();
//    public ArrayList<String> classmatesNames = new ArrayList<>();
//    public ArrayList<String> teachersNames = new ArrayList<>();
    final RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
    private ImageLoader imageLoader;
    private ProgressDialog dialog;

    Context context = this;

    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        imageLoader = VolleySingleton.getInstance().getImageLoader();

        initialize();

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.login_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLogIn.setAdapter(spinnerAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void initialize(){
        userName = (EditText)findViewById(R.id.userName);
        passWord = (EditText)findViewById(R.id.passWord);
        rememberPassword = (CheckBox)findViewById(R.id.remember);
        login = (Button)findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        sLogIn = (Spinner) findViewById(R.id.sp_login_type);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.login:

                if(isEmpty(userName))
                    Toast.makeText(LoginPage.this, "Username is empty!", Toast.LENGTH_LONG).show();

                else if(isEmpty(passWord))
                    Toast.makeText(LoginPage.this, "Password is empty!", Toast.LENGTH_LONG).show();

                else {

                    String login = sLogIn.getSelectedItem().toString().trim();

                    username = userName.getText().toString().trim();
                    password = passWord.getText().toString().trim();

                    JsonObjectRequest request = null;

                    if(login.equals("Student")) {
                        request = new JsonObjectRequest(Request.Method.GET,
                                getStudentRequestUrl(username, password),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            id = Integer.parseInt(response.getString(KEY_STUDENT_ID));
                                            String firstName = response.getString(KEY_FIRST_NAME);
                                            String middleName = response.getString(KEY_MIDDLE_NAME);
                                            String lastName = response.getString(KEY_LAST_NAME);
                                            String allGroups = response.getString(KEY_GROUPS_LIST);
                                            String allPosts = response.getString(KEY_WALL_LIST);
                                            String allMates = response.getString(KEY_CLASSMATES_LIST);
                                            String allTeachers = response.getString(KEY_TEACHERS_LIST);
                                            String profilePic = response.getString(KEY_PROFILE_PICTURE);
                                            badges = response.getString(KEY_BADGES);
                                            recentBadges = response.getString(KEY_RECENT_BADGES);

//                                        extractGroupIDs(allGroups);
//                                        extractClassMateIDs(allMates);
                                            extractPostIDs(allPosts);
//                                        extractTeacherIDs(allTeachers);

                                            name = firstName + " " + middleName + " " + lastName;
                                            dialog = new ProgressDialog(LoginPage.this);
                                            dialog.setMessage("Downloading info");
                                            dialog.show();

                                            imageLoader.get(getStudentDisplayPicRequestUrl(profilePic), new ImageLoader.ImageListener() {
                                                @Override
                                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                                    dispPic = response.getBitmap();
                                                    dialog.dismiss();
                                                    changeActivity();
                                                }

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("Log in", "Display pic error");

                                                }
                                            });

//                                        extractGroupTitles();
//                                        extractMatesNames();
//                                        extractTeachersNames();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("error", "ERROR!");

                                    }
                                }
                        );
                    }


                    else if(login.equals("Teacher")) {
                        request = new JsonObjectRequest(Request.Method.GET,
                                getTeacherRequestUrl(username, password),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
//                                                id = Integer.parseInt(response.getString(KEY_STUDENT_ID));
                                            String firstName = response.getString(KEY_FIRST_NAME);
                                            String middleName = response.getString(KEY_MIDDLE_NAME);
                                            String lastName = response.getString(KEY_LAST_NAME);
//                                                String allGroups = response.getString(KEY_GROUPS_LIST);
                                            String allPosts = response.getString(KEY_WALL_LIST);
//                                                String allMates = response.getString(KEY_CLASSMATES_LIST);
//                                                String allTeachers = response.getString(KEY_TEACHERS_LIST);
                                            String profilePic = response.getString(KEY_PROFILE_PICTURE);
//                                            badges = response.getString(KEY_BADGES);
//                                            recentBadges = response.getString(KEY_RECENT_BADGES);

//                                        extractGroupIDs(allGroups);
//                                        extractClassMateIDs(allMates);
                                            extractPostIDs(allPosts);
//                                        extractTeacherIDs(allTeachers);

                                            name = firstName + " " + middleName + " " + lastName;
                                            dialog = new ProgressDialog(LoginPage.this);
                                            dialog.setMessage("Downloading info");
                                            dialog.show();

                                            imageLoader.get(getTeacherDisplayPicRequestUrl(profilePic), new ImageLoader.ImageListener() {
                                                @Override
                                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                                    dispPic = response.getBitmap();
                                                    dialog.dismiss();
                                                    changeActivity();
                                                }

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("Log in", "Display pic error");

                                                }
                                            });

//                                        extractGroupTitles();
//                                        extractMatesNames();
//                                        extractTeachersNames();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, "Teacher log in error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        );
                    }


                    else if (login.equals("Parent")) {

                            request = new JsonObjectRequest(Request.Method.GET,
                                    getParentRequestUrl(username, password),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
//                                                id = Integer.parseInt(response.getString(KEY_STUDENT_ID));
                                                String firstName = response.getString(KEY_FIRST_NAME);
                                                String middleName = response.getString(KEY_MIDDLE_NAME);
                                                String lastName = response.getString(KEY_LAST_NAME);
//                                                String allGroups = response.getString(KEY_GROUPS_LIST);
//                                                String allPosts = response.getString(KEY_WALL_LIST);
//                                                String allMates = response.getString(KEY_CLASSMATES_LIST);
//                                                String allTeachers = response.getString(KEY_TEACHERS_LIST);
                                                String profilePic = response.getString(KEY_PROFILE_PICTURE);
//                                                badges = response.getString(KEY_BADGES);
//                                                recentBadges = response.getString(KEY_RECENT_BADGES);

//                                        extractGroupIDs(allGroups);
//                                        extractClassMateIDs(allMates);
//                                                extractPostIDs(allPosts);
//                                        extractTeacherIDs(allTeachers);

                                                name = firstName + " " + middleName + " " + lastName;
                                                dialog  = new ProgressDialog(LoginPage.this);
                                                dialog.setMessage("Downloading info");
                                                dialog.show();

                                                imageLoader.get(getParentDisplayPicRequestUrl(profilePic), new ImageLoader.ImageListener() {
                                                    @Override
                                                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                                        dispPic = response.getBitmap();
                                                        dialog.dismiss();
                                                        changeActivity();
                                                    }

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("Log in", "Display pic error");

                                                    }
                                                });

//                                        extractGroupTitles();
//                                        extractMatesNames();
//                                        extractTeachersNames();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("error", "ERROR!");

                                        }
                                    }
                            );

                    }
                    requestQueue.add(request);

                }

                break;

            case R.id.signup:
                final Dialog dialog = new Dialog(this);
                Button student, teacher, parent;

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_signup);
                dialog.setCanceledOnTouchOutside(true);

                student = (Button) dialog.findViewById(R.id.student);
                parent = (Button) dialog.findViewById(R.id.parent);
                teacher = (Button) dialog.findViewById(R.id.teacher);

                student.setOnClickListener(this);
                teacher.setOnClickListener(this);
                parent.setOnClickListener(this);

                dialog.show();
                break;

            case R.id.parent:
                Intent intent1 = new Intent(LoginPage.this, SignUpStudent.class);
                intent1.putExtra("Button", 'p');
                startActivity(intent1);
                break;

            case R.id.teacher:
                Intent intent2 = new Intent(LoginPage.this, SignUpStudent.class);
                intent2.putExtra("Button", 't');
                startActivity(intent2);
                break;

            case R.id.student:
                Intent intent3 = new Intent(LoginPage.this, SignUpStudent.class);
                intent3.putExtra("Button", 's');
                startActivity(intent3);
                break;
        }

    }

    private void extractPostIDs(String allPosts) {
        String[] array = allPosts.split(",");
        for(String s: array)
            wallList.add(Integer.parseInt(s));

    }

//    private void extractClassMateIDs(String allMates) {
//        String[] array = allMates.split(",");
//        for(String s: array)
//            classmatesList.add(Integer.parseInt(s));
//
//
//    }
//
//    private void extractGroupIDs(String allGroups) {
//        String[] array = allGroups.split(",");
//        for(String s: array)
//            groupsList.add(Integer.parseInt(s));
//    }
//
//    private void extractTeacherIDs(String allTeachers) {
//        String[] array = allTeachers.split(",");
//        for(String s: array)
//            teachersList.add(Integer.parseInt(s));
//
//    }

    private String getGroupTitleRequestUrl(Integer integer) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                URL_TYPE_GROUP_TITLE+
                URL_CHAR_AMPERSAND+
                URL_GROUP_ID+
                URL_CHAR_EQUAL+
                integer.toString();
    }

    private String getStudentDisplayPicRequestUrl(String url) {
        return URL_PROFILE_PICTURE + "students" + URL_PROFILE_IMAGE + url;
    }

    private String getTeacherDisplayPicRequestUrl(String url) {
        String str = URL_PROFILE_PICTURE + "teachers" + URL_PROFILE_IMAGE + url;
        Log.d("Mappr", str);
        return str;
    }

    private String getParentDisplayPicRequestUrl(String url) {
        return URL_PROFILE_PICTURE + "parents" + URL_PROFILE_IMAGE + url;
    }

    private String getMateNameRequestUrl(Integer integer) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                URL_TYPE_STUDENT_NAME+
                URL_CHAR_AMPERSAND+
                URL_STUDENT_ID+
                URL_CHAR_EQUAL+
                integer.toString();
    }

    private String getTeacherNameRequestUrl(Integer integer) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                URL_TYPE_TEACHER_NAME+
                URL_CHAR_AMPERSAND+
                URL_TEACHER_ID+
                URL_CHAR_EQUAL+
                integer.toString();
    }



    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private String getTeacherRequestUrl(String username, String password) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                "teacher_login"+
                URL_CHAR_AMPERSAND+
                URL_USERNAME+
                URL_CHAR_EQUAL+
                username+
                URL_CHAR_AMPERSAND+
                URL_PASSWORD+
                URL_CHAR_EQUAL+
                password;
    }

    private String getParentRequestUrl(String username, String password) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                "parent_login"+
                URL_CHAR_AMPERSAND+
                URL_USERNAME+
                URL_CHAR_EQUAL+
                username+
                URL_CHAR_AMPERSAND+
                URL_PASSWORD+
                URL_CHAR_EQUAL+
                password;
    }

    private String getStudentRequestUrl(String username, String password) {
        return URL_LOG_IN+
                URL_CHAR_QUESTION+
                URL_REQUEST_TYPE+
                URL_CHAR_EQUAL+
                "student_login"+
                URL_CHAR_AMPERSAND+
                URL_USERNAME+
                URL_CHAR_EQUAL+
                username+
                URL_CHAR_AMPERSAND+
                URL_PASSWORD+
                URL_CHAR_EQUAL+
                password;

    }

//    private void extractGroupTitles() {
//        JsonObjectRequest groupRequest;
//        for(i = 0; i < groupsList.size(); i++) {
//            groupRequest = new JsonObjectRequest(Request.Method.GET,
//                    getGroupTitleRequestUrl(groupsList.get(i)),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                String currentTitle = response.getString(KEY_GROUPS_TITLE);
//
//                                    groupsTitles.add(currentTitle);
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("error", error.getMessage());
//
//                        }
//                    });
//
//            requestQueue.add(groupRequest);
//
//        }
//    }
//
//    private void extractMatesNames() {
//
//        JsonObjectRequest groupRequest;
//        for(i = 0; i < classmatesList.size(); i++) {
//            groupRequest = new JsonObjectRequest(Request.Method.GET,
//                    getMateNameRequestUrl(classmatesList.get(i)),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                String currentTitle = response.getString(KEY_STUDENT_NAME);
//
//                                classmatesNames.add(currentTitle);
//
////                                if(classmatesList.size() == classmatesNames.size()) {
////                                    Log.d("dialog", "dismiss");
////                                    dialog.dismiss();
////                                    changeActivity();
////
////                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("error", "Classmate volley error");
//
//                        }
//                    });
//
//            requestQueue.add(groupRequest);
//        }
//    }
//
//    private void extractTeachersNames() {
//
//        JsonObjectRequest groupRequest;
//        for(i = 0; i < teachersList.size(); i++) {
//            groupRequest = new JsonObjectRequest(Request.Method.GET,
//                    getTeacherNameRequestUrl(teachersList.get(i)),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                String currentTitle = response.getString(KEY_TEACHER_NAME);
//
//                                teachersNames.add(currentTitle);
//
//                                if(teachersList.size() == teachersNames.size()) {
//                                    Log.d("dialog", "dismiss");
//                                    dialog.dismiss();
//                                    changeActivity();
//
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("error", error.getMessage());
//                        }
//                    });
//            requestQueue.add(groupRequest);
//        }
//    }

    /**
     * Put extras in intent and start Home Activity
     */
    private void changeActivity() {

        Intent intent = new Intent(LoginPage.this, HomeActivity.class);
//        intent.putStringArrayListExtra("groupTitles", groupsTitles);
//        intent.putStringArrayListExtra("matesNames", classmatesNames);
        intent.putIntegerArrayListExtra("postsIds", wallList);
//        intent.putStringArrayListExtra("teachersNames", teachersNames);
        startActivity(intent);
    }




}
