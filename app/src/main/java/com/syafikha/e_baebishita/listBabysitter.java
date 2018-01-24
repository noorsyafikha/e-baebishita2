package com.syafikha.e_baebishita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class listBabysitter extends AppCompatActivity {
    RecyclerView rvListBabysitter;
    CardView cvListBabysitter;
    //TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_babysitter);

       // txtView=(TextView) findViewById(R.id.tv);
        rvListBabysitter = (RecyclerView) findViewById(R.id.rvBabysitter);
        cvListBabysitter = (CardView) findViewById(R.id.cvBabysitter);
        rvListBabysitter.setHasFixedSize(true);

        //final String area1=getIntent().getStringExtra("babysitter_area");
        //txtView.setText(area1);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvListBabysitter.setLayoutManager(manager);
        String url ="https://e-baebishita.000webhostapp.com/listBabysitter.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<babysitter> babyList = new JsonConverter<babysitter>().toArrayList(response,babysitter.class);
                        babysitterAdapter adapter = new babysitterAdapter(getApplicationContext(),babyList);
                        rvListBabysitter.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null)
                {
                    Toast.makeText(getApplicationContext(),"Error while reading",Toast.LENGTH_LONG).show();
                }
            }
        })
{
       /* @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();

            params.put("babysitter_area",area1);


            return params;
        }*/
    };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
