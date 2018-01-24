package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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

public class ratingDetails extends AppCompatActivity implements View.OnClickListener{
    TextView bookingId,parentHp,babysitterHp,text;
    RatingBar rb;
    Button backBtn,rateBtn;
    String phoneNum;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_details);
        bookingId=(TextView) findViewById(R.id.txtBookingID);
        parentHp=(TextView) findViewById(R.id.txtParentHP);
        babysitterHp=(TextView) findViewById(R.id.txtBabysitterHP);
        backBtn=(Button) findViewById(R.id.btnBackRate);

        backBtn.setOnClickListener(this);

        listenerForRatingBar();
        onButtonClickListener();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // process
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();


        /*if(sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                phoneNum = info.getString("parent_phoneNum");


            } catch (Exception e) { e.printStackTrace(); }
        }*/

        if(getIntent().getSerializableExtra("booking") != null){
            booking book = (booking) getIntent().getSerializableExtra("booking");

            bookingId.setText(book.bookingID);
            babysitterHp.setText(book.babysitter_phoneNum);
            parentHp.setText(book.parent_phoneNum);

        }
       // viewData();
    }
    /*private void viewData() {

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

              parentHp.setText(id);


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

    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackRate:
                Intent profile1 = new Intent(getApplicationContext(), rate.class);
                startActivity(profile1);
                break;
        }
    }
        public  void listenerForRatingBar() {
        rb= (RatingBar) findViewById(R.id.ratingBar);
        text=(TextView) findViewById(R.id.txtText);


        rb.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text.setText(String.valueOf(rating));
                    }
                }
        );
    }

    public void onButtonClickListener() {
        rb = (RatingBar) findViewById(R.id.ratingBar);
         rateBtn= (Button)findViewById(R.id.btnRate);

       rateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ratingDetails.this,
                                String.valueOf(rb.getRating()),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }



    }

