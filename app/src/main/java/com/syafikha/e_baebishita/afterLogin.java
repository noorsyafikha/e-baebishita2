package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class afterLogin extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;
    Button bookBtn, manageBtn,rateBtn, profileBtn, faqBtn, termBtn, logoutBtn;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        bookBtn=(Button) findViewById(R.id.btnBook);
        manageBtn=(Button) findViewById(R.id.btnManageBook);
        rateBtn=(Button) findViewById(R.id.btnRate);
        profileBtn=(Button) findViewById(R.id.btnProfile);
        faqBtn=(Button) findViewById(R.id.btnFAQ);
        termBtn=(Button) findViewById(R.id.btnTerm);
        logoutBtn=(Button) findViewById(R.id.btnLogout);
        name=(TextView) findViewById(R.id.txtParentName);

        bookBtn.setOnClickListener(this);
        manageBtn.setOnClickListener(this);
        rateBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);
        faqBtn.setOnClickListener(this);
        termBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        // process
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();

        if(sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                name.setText("Welcome : " + info.getString("parent_name"));

            } catch (Exception e) { e.printStackTrace(); }
        } else {
            try {
                JSONObject info = new JSONObject(getIntent().getStringExtra("info"));


                name.setText("Welcome " + info.getString("parent_name"));

            } catch (Exception e) { e.printStackTrace(); }
        }

    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBook:
                Intent i = new Intent(getApplicationContext(),book.class);
                startActivity(i);
                break;
            case R.id.btnManageBook:
                i = new Intent(getApplicationContext(),manageBook.class);
                startActivity(i);
                break;
            case R.id.btnRate:
                i = new Intent(getApplicationContext(), rate.class);
                startActivity(i);
                break;
            case R.id.btnProfile:
                i = new Intent(getApplicationContext(), profile.class);
                startActivity(i);
                break;
            case R.id.btnFAQ:
                i = new Intent(getApplicationContext(), FAQ.class);
                startActivity(i);
                break;
            case R.id.btnTerm:
                i = new Intent(getApplicationContext(),term.class);
                startActivity(i);
                break;
            case R.id.btnLogout:
                shEditor.clear();                                      // clear the session data in shared preference
                shEditor.commit();                                     // save current shared preference state
                finish();                                              // destroy this activity
                startActivity(new Intent(this, MainActivity.class));   // start new main activity
                break;

        }
    }
}
