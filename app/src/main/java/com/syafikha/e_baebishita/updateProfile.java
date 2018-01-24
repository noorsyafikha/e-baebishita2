package com.syafikha.e_baebishita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updateProfile extends AppCompatActivity implements View.OnClickListener  {
    EditText editName,editPhone,editPassword, editAddress, editReligion;

    Button backBtn,updateBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String uname,phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editName=(EditText)findViewById(R.id.edtName);
        editPhone=(EditText) findViewById(R.id.edtPhone);
        editPassword=(EditText) findViewById(R.id.edtPassword);
        editAddress=(EditText) findViewById(R.id.edtAddress);
        editReligion=(EditText) findViewById(R.id.edtReligion);
        backBtn=(Button)findViewById(R.id.btnBackUpdateProfile);
        updateBtn=(Button) findViewById(R.id.btnUpdate2);

        backBtn.setOnClickListener(this);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();

        if (sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                uname = info.getString("parent_name");
                phoneNum = info.getString("parent_phoneNum");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        viewData();
        updateBtn.setOnClickListener(this);
    }

    private void viewData() {

        String url = "https://e-baebishita.000webhostapp.com/viewProfile.php";
        StringRequest dataRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name1,phone1,password1, address1,religion1;

                name1 = "";
                phone1 = "";
                password1 = "";
                address1 = "";
                religion1 = "";

                try {
                    JSONObject data = new JSONObject(response);

                    name1 = data.getString("parent_name").toString();
                    phone1 = data.getString("parent_phoneNum").toString();
                    password1 = data.getString("parent_password").toString();
                    address1 = data.getString("parent_address").toString();
                    religion1= data.getString("parent_religion").toString();


                } catch (Exception e) { e.printStackTrace(); }

                editName.setText(name1);
                editPhone.setText(phone1);
                editPassword.setText(password1);
                editAddress.setText(address1);
                editReligion.setText(religion1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("parent_phoneNum", phoneNum);
                return params;
            }
        };

        requestQueue.add(dataRequest);
    }

    @Override
    public void onClick(View v) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        switch (v.getId())
        {

            case R.id.btnUpdate2:
                String url ="https://e-baebishita.000webhostapp.com/updateProfile.php";
                try
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);

                                if (jsonResponse.getString("status").equalsIgnoreCase("1")) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                                    Intent profile = new Intent(getApplicationContext(), afterLogin.class);

                                    startActivity(profile);
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();


                        }
                    }  )

                    {
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String, String> params = new HashMap<>();

                            params.put("parent_name",editName.getText().toString());
                            params.put("phoneNum", editPhone.getText().toString());
                            params.put("parent_password", editPassword.getText().toString());
                            params.put("parent_address", editAddress.getText().toString());
                            params.put("parent_religion", editReligion.getText().toString());

                            params.put("parent_phoneNum", phoneNum);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);

                }catch (Exception e) {e.printStackTrace();}
                break;

            case R.id.btnBackUpdateProfile:
                Intent i = new Intent(getApplication(),profile.class);
                startActivity(i);
                break;
        }

    }
}

