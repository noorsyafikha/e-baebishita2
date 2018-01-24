package com.syafikha.e_baebishita;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{

    EditText name, phone, password, address;
    Spinner religionSpinner;
    Button signUpBtn;

    ProgressDialog progressDialog;
    //public int typeID = 0;
    public static String Tag = MainActivity.class.getSimpleName();

    private String spinner;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=(EditText) findViewById(R.id.edtName);
        phone=(EditText) findViewById(R.id.edtPhoneNum);
        password=(EditText) findViewById(R.id.edtPassword);
        address=(EditText) findViewById(R.id.edtAddress);
        religionSpinner=(Spinner) findViewById(R.id.spinner);
        signUpBtn=(Button) findViewById(R.id.btnSignUp2);

        signUpBtn.setOnClickListener(this);
        religionSpinner.setOnItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onClick(View v) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        switch (v.getId())
        {
            case R.id.btnSignUp2:
                try
                {

                    String url = "https://e-baebishita.000webhostapp.com/register.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (jsonResponse.getString("status").equalsIgnoreCase("1")) {
                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                                        Intent profile = new Intent(getApplicationContext(), afterLogin.class);

                                        profile.putExtra("info", response);

                                        startActivity(profile);
                                        finish();
                                    } else
                                        Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }  )
                    {
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String, String> params;

                            params = new HashMap<>();
                            params.put("parent_name",name.getText().toString());
                            params.put("parent_phoneNum",phone.getText().toString());
                            params.put("parent_password", password.getText().toString());
                            params.put("parent_address", address.getText().toString());
                            params.put("parent_religion",spinner);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                    progressDialog.show();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner= "" + id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
