package com.example.priyanshu.mappr.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.priyanshu.mappr.R;


public class LoginPage extends ActionBarActivity implements View.OnClickListener {

    private EditText userName;
    private EditText passWord;
    private CheckBox rememberPassword;
    private Button login, signup;



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
        signup = (Button) findViewById(R.id.singup);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.login:
                Intent intent = new Intent(LoginPage.this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.singup:
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
}
