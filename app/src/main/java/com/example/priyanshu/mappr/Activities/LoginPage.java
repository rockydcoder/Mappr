package com.example.priyanshu.mappr.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.priyanshu.mappr.Extras.Keys.LogIn.KEY_FIRST_NAME;
import static com.example.priyanshu.mappr.Extras.Keys.LogIn.KEY_LAST_NAME;
import static com.example.priyanshu.mappr.Extras.Keys.LogIn.KEY_MIDDLE_NAME;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_AMPERSAND;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_EQUAL;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_CHAR_QUESTION;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_LOG_IN;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_PASSWORD;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_REQUEST_TYPE;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_USERNAME;


public class LoginPage extends ActionBarActivity implements View.OnClickListener {

    private EditText userName;
    private EditText passWord;
    private CheckBox rememberPassword;
    private Button login, signup;
    private String username = null;
    private String password = null;
    public static String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initialize();

        login.setOnClickListener(this);
        signup.setOnClickListener(this);



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

                    username = userName.getText().toString().trim();
                    password = passWord.getText().toString().trim();


                    RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                            getRequestUrl(username, password),
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String firstName = response.getString(KEY_FIRST_NAME);
                                        String middleName = response.getString(KEY_MIDDLE_NAME);
                                        String lastName = response.getString(KEY_LAST_NAME);

                                        name = firstName + " " + middleName + " " + lastName;

                                        Intent intent = new Intent(LoginPage.this, HomeActivity.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("error", error.getMessage());

                                }
                            }
                            );


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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private String getRequestUrl(String username, String password) {
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


}
