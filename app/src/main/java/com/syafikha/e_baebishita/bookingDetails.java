package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class bookingDetails extends AppCompatActivity implements View.OnClickListener{
    TextView bookingID, babysitterPhone, dateStart, dateEnd, timeStart, timeEnd, area, packages,loc,specialNeed,additional,parentphone, noChild;
    Button updateBtn, backBtn,deleteBtn;
    String uname,phoneNum,bookID;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        bookingID=(TextView) findViewById(R.id.txtBookID);
        babysitterPhone=(TextView) findViewById(R.id.txtBabysitterHp);
        dateStart=(TextView)findViewById(R.id.txtDateS);
        dateEnd=(TextView) findViewById(R.id.txtDateE);
        timeStart=(TextView)findViewById(R.id.txtTimeS);
        timeEnd=(TextView) findViewById(R.id.txtTimeE);
        area=(TextView) findViewById(R.id.txtArea);
        packages=(TextView) findViewById(R.id.txtPackages);
        loc=(TextView) findViewById(R.id.txtLocation);
        specialNeed=(TextView) findViewById(R.id.txtSpecial);
        additional=(TextView) findViewById(R.id.txtAdditional);
        parentphone=(TextView) findViewById(R.id.txtParenthp);
        noChild=(TextView) findViewById(R.id.txtNoChild);
        updateBtn=(Button)findViewById(R.id.btnRate);
        backBtn=(Button) findViewById(R.id.btnBackUpdateBook);
        deleteBtn=(Button) findViewById(R.id.btnDelete);


        updateBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // process
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();


        if(sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                uname = info.getString("parent_name");
                phoneNum = info.getString("parent_phoneNum");
               // id = info.getString("bookingID");

            } catch (Exception e) { e.printStackTrace(); }
        }

        if(getIntent().getSerializableExtra("booking") != null){
            booking book = (booking) getIntent().getSerializableExtra("booking");

            bookingID.setText(book.bookingID);
           babysitterPhone.setText(book.babysitter_phoneNum);
            parentphone.setText(book.parent_phoneNum);
            dateStart.setText(book.dateStart);
            dateEnd.setText(book.dateEnd);
            timeStart.setText(book.timeStart);
            timeEnd.setText(book.timeEnd);
            area.setText(book.area);
            loc.setText(book.location);
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
                Intent profile1 = new Intent(getApplicationContext(), updateBooking1.class);
                startActivity(profile1);
                break;

            case R.id.btnBackUpdateBook:
                Intent profile = new Intent(getApplicationContext(), manageBook.class);
                startActivity(profile);
                break;

            case  R.id.btnDelete:

                 String url = "https://e-baebishita.000webhostapp.com/deleteBooking.php" ;
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

