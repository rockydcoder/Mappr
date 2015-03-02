package com.example.priyanshu.mappr.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.priyanshu.mappr.R;


public class SignUpStudent extends ActionBarActivity {
    private EditText etGroup, etUser, etPass, etMail, etFirst, etLast;
    private TextView tvTitle;
    private Spinner spTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        char source = bundle.getChar("Button");
        setContentView(R.layout.activity_sign_up_student);

        etGroup = (EditText) findViewById(R.id.editText3);
        etUser = (EditText) findViewById(R.id.editText4);
        etPass = (EditText) findViewById(R.id.editText5);
        etMail = (EditText) findViewById(R.id.editText6);
        etFirst = (EditText) findViewById(R.id.editText7);
        etLast = (EditText) findViewById(R.id.editText8);

        tvTitle = (TextView) findViewById(R.id.textView5);
        spTitle = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.title_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spTitle.setAdapter(adapter);

        switch (source) {
            case 's':
                tvTitle.setVisibility(View.GONE);
                spTitle.setVisibility(View.GONE);
                break;

            case 't':
                etGroup.setVisibility(View.GONE);
                etUser.setVisibility(View.GONE);
                break;

            case 'p':
                etGroup.setVisibility(View.GONE);
                etUser.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                spTitle.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_student, menu);
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
}
