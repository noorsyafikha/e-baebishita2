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

public class updatebooking extends AppCompatActivity implements View.OnClickListener{
    TextView bookingId,parentPhone,babysitterPhone;
    EditText dateStart, dateEnd, timeStart, timeEnd,area,location,packages,noChild, specialNeed,additional;
    Button backBtn, updateBtn;

    String uname,phoneNum;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebooking);

        bookingId=(TextView) findViewById(R.id.txtBookingID);
        parentPhone=(TextView) findViewById(R.id.txtParentHP);
        babysitterPhone=(TextView) findViewById(R.id.txtBabysitterHp);
        dateStart=(EditText) findViewById(R.id.edtDateStart);
        dateEnd=(EditText) findViewById(R.id.edtDateEnd);
        timeStart=(EditText) findViewById(R.id.edtTimeStart);
        timeEnd=(EditText) findViewById(R.id.edtTimeStart);
        area=(EditText) findViewById(R.id.edtArea);
        location=(EditText)findViewById(R.id.edtLocation);
        packages=(EditText) findViewById(R.id.edtPackages);
        noChild=(EditText) findViewById(R.id.edtNumChild);
        specialNeed=(EditText) findViewById(R.id.edtSpecial);
        additional=(EditText) findViewById(R.id.edtAdditional);
        backBtn=(Button) findViewById(R.id.btnBack);
        updateBtn=(Button) findViewById(R.id.btnUpdate3);

        updateBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);


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
            booking book = (booking) getIntent().getSerializableExtra("booking");

            bookingId.setText(book.bookingID);
            babysitterPhone.setText(book.babysitter_phoneNum);
            parentPhone.setText(book.parent_phoneNum);
            dateStart.setText(book.dateStart);
            dateEnd.setText(book.dateEnd);
            timeStart.setText(book.timeStart);
            timeEnd.setText(book.timeEnd);
            area.setText(book.area);
            location.setText(book.location);
            packages.setText(book.packages);
            noChild.setText(book.numOfChild);
            specialNeed.setText(book.specialNeed);
            additional.setText(book.additionalInfo);


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

                parentPhone.setText(id);


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

                                    Intent profile = new Intent(getApplicationContext(), bookingDetails.class);

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

                            params.put("dateStart",dateStart.getText().toString());
                            params.put("dateEnd", dateEnd.getText().toString());
                            params.put("timeStart", timeStart.getText().toString());
                            params.put("timeEnd", timeEnd.getText().toString());
                            params.put("area", area.getText().toString());
                            params.put("location", location.getText().toString());
                            params.put("packages", packages.getText().toString());
                            params.put("numOfChild", noChild.getText().toString());
                            params.put("specialNeed", specialNeed.getText().toString());
                            params.put("additionalInfo", additional.getText().toString());


                            params.put("parent_phoneNum", phoneNum);

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);

                }catch (Exception e) {e.printStackTrace();}
                break;

            case R.id.btnBack:
                Intent profile = new Intent(getApplicationContext(), bookingDetails.class);
                startActivity(profile);
                break;


        }
    }



}


