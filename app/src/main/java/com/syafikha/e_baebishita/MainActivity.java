package com.syafikha.e_baebishita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bSignUp,bLogin;
    EditText phoneLogin, passwordLogin;
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneLogin = (EditText) findViewById(R.id.edtPhoneNumLogin);
        passwordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        bSignUp = (Button) findViewById(R.id.btnSignUp1);
        bLogin = (Button) findViewById(R.id.btnLogin);

        bLogin.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        // process
        requestQueue = Volley.newRequestQueue(this); // initialize request queue from Volley
        sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE); // shared preference is used to maintain login session

        // check if user is currently logged in or not, if yes, proceed to profile page
        if(!sharedPreferences.getString("user-info", "").isEmpty()) {
            startActivity(new Intent(this, afterLogin.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btnSignUp1: // register button click action
                // create new intent to change page from this page to the Register page
                Intent registerPage = new Intent(this, signUp.class);

                // put extra to carry data from this page to the register page
                registerPage.putExtra("parent_phoneNum", phoneLogin.getText().toString());
                registerPage.putExtra("parent_password",passwordLogin.getText().toString());

                startActivity(registerPage); // start the register page activity
                break;

            case R.id.btnLogin: // login button click action
                final String phone, password;
                String url;

                url = "https://e-baebishita.000webhostapp.com/login.php"; // url for login service

                // get email and password entered by user
                phone = phoneLogin.getText().toString();
                password = passwordLogin.getText().toString();

                // initialize progress dialog --> to be displayed during loading
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);

                try {
                    // create new Volley StringRequest to request service from server and get a string response
                    // new StringRequest(<request method>, <url>, <response listener action>, <error listener actions>);
                    StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { // a method that define the action to be taken when Android get http response from server
                            try {
                                if(!response.isEmpty()) { // if the response is not empty (there are user record in the database)
                                    shEditor = sharedPreferences.edit(); // initialize shared preference editor

                                    shEditor.putString("user-info", response); // put user data into shared preference to maintain login session
                                    shEditor.commit();

                                    // start profile activity
                                    Intent profile = new Intent(getApplicationContext(), afterLogin.class);
                                    profile.putExtra("info", response);

                                    startActivity(profile);
                                    finish();
                                } else // if response is empty (there are no user record in the database)
                                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show(); // show error message
                            } catch (Exception e) { e.printStackTrace(); }

                            // if progress dialog is still showing, dismiss it
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { // a method that define the action to be taken when there are error occurred
                            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show(); // show error message

                            // if progress dialog is still showing, dismiss it
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError { // a method used to provide parameter during the http request
                            Map<String, String> params = new HashMap<>();

                            // send email and password as login parameters
                            params.put("parent_phoneNum", phone);
                            params.put("parent_password", password);

                            return params;
                        }
                    };

                    requestQueue.add(loginRequest); // add new request into the Volley queue and Volley will automatically run the request
                    progressDialog.show(); // show progress dialog
                } catch (Exception e) { e.printStackTrace(); }
                break;
        }
    }
    }
