package com.syafikha.e_baebishita;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class rate extends AppCompatActivity {
    RecyclerView rvListRating;
    CardView cvListRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rvListRating = (RecyclerView) findViewById(R.id.rvRating);
        cvListRating= (CardView) findViewById(R.id.cvRating);
        rvListRating.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvListRating.setLayoutManager(manager);
        String url ="https://e-baebishita.000webhostapp.com/listBooking.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<booking> babyList = new JsonConverter<booking>().toArrayList(response,booking.class);
                        ratingAdapter adapter = new ratingAdapter(getApplicationContext(),babyList);
                        rvListRating.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null)
                {
                    Toast.makeText(getApplicationContext(),"Error while reading",Toast.LENGTH_LONG).show();
                }
            }
        }
        );
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}
