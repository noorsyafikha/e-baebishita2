package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class updateBooking1 extends AppCompatActivity implements View.OnClickListener {
    TextView bookingID, babysitterPhone,parentphone;
    Button updateBtn, backBtn,delBtn;
    String uname,phoneNum;
    EditText dateS,dateE,timeS,timeE,area,location,packages,numChild,special,additional;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking1);

        bookingID=(TextView) findViewById(R.id.txtBookID);
        babysitterPhone=(TextView) findViewById(R.id.txtBabysitterHp);
        parentphone=(TextView) findViewById(R.id.txtParenthp);
        dateS=(EditText) findViewById(R.id.edtDateStart);
        dateE=(EditText) findViewById(R.id.edtDateEnd);
        timeS=(EditText) findViewById(R.id.edtTimeS);
        timeE=(EditText) findViewById(R.id.edtTimeStart);
        area=(EditText) findViewById(R.id.edtArea);
        packages=(EditText) findViewById(R.id.edtPackages);
        location=(EditText) findViewById(R.id.edtLocation);
        special=(EditText) findViewById(R.id.edtSpecial);
        additional=(EditText) findViewById(R.id.edtAdditional);
        numChild=(EditText) findViewById(R.id.edtNoChild);
        updateBtn=(Button)findViewById(R.id.btnRate);
        backBtn=(Button) findViewById(R.id.btnBackUpdateBook);
        delBtn=(Button) findViewById(R.id.btnDel);

        updateBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // process
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();


        if(sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                uname = info.getString("parent_name");
                phoneNum = info.getString("parent_phoneNum");


            } catch (Exception e) { e.printStackTrace(); }
        }

        if(getIntent().getSerializableExtra("booking") != null){
            booking book1 = (booking) getIntent().getSerializableExtra("booking");

            bookingID.setText(book1.bookingID);
            babysitterPhone.setText(book1.babysitter_phoneNum);
            parentphone.setText(book1.parent_phoneNum);
            dateS.setText(book1.dateStart);
            dateE.setText(book1.dateEnd);
            timeS.setText(book1.timeStart);
            timeE.setText(book1.timeEnd);
            area.setText(book1.area);
            location.setText(book1.location);
            packages.setText(book1.packages);
            numChild.setText(book1.numOfChild);
            special.setText(book1.specialNeed);
            additional.setText(book1.additionalInfo);

        }
        viewData();
    }
    private void viewData() {

        String url = "https://e-baebishita.000webhostapp.com/viewProfile.php";
        StringRequest dataRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String id;

                id = "";

                try {
                    JSONObject data = new JSONObject(response);

                    id = data.getString("parent_phoneNum").toString();

                } catch (Exception e) { e.printStackTrace(); }

                parentphone.setText(id);


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
                params.put("parent_name", uname);

                return params;
            }
        };

        requestQueue.add(dataRequest);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRate:
                String url ="https://e-baebishita.000webhostapp.com/updateBooking.php";
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

                            params.put("dateStart",dateS.getText().toString());
                            params.put("dateEnd", dateE.getText().toString());
                            params.put("timeStart", timeS.getText().toString());
                            params.put("timeEnd", timeE.getText().toString());
                            params.put("area", area.getText().toString());
                            params.put("location", location.getText().toString());
                            params.put("packages", packages.getText().toString());
                            params.put("numOfChild", numChild.getText().toString());
                            params.put("specialNeed", special.getText().toString());
                            params.put("additionalInfo", additional.getText().toString());
                            params.put("bookingID",bookingID.getText().toString());


                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);

                }catch (Exception e) {e.printStackTrace();}
                break;

            case R.id.btnBackUpdateBook:
                Intent profile1 = new Intent(getApplicationContext(), manageBook.class);
                startActivity(profile1);
                break;

            case  R.id.btnDel:

                url = "https://e-baebishita.000webhostapp.com/deleteBooking.php" ;
                StringRequest del = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                        shEditor.clear();                                      // clear the session data in shared preference
                        shEditor.commit();                                     // save current shared preference state
                        finish();                                               // destroy this activity

                        Intent in = new Intent(getApplicationContext(),afterLogin.class);
                        startActivity(in);

                    }},new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("parent_phoneNum", phoneNum);
                        params.put("bookingID",bookingID.getText().toString());

                        return params;
                    }
                };
                requestQueue.add(del);
                break;

        }
    }



}


