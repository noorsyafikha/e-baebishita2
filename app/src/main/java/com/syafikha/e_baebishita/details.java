package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class details extends AppCompatActivity  implements View.OnClickListener{
    ImageView ivImage;
    TextView parenthp, BName, BPhoneNum,BAddress, BArea,BReligion, BExperinces,pricePerHour,pricePerDay;
    Button chooseBtn, backBtn;
    String uname,phoneNum;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        parenthp=(TextView) findViewById(R.id.txtParentHP);
        BName=(TextView) findViewById(R.id.txtBName);
        BPhoneNum=(TextView) findViewById(R.id.txtBPhone);
        BAddress=(TextView) findViewById(R.id.txtBAddress);
        BArea=(TextView) findViewById(R.id.txtBArea);
        BReligion=(TextView) findViewById(R.id.txtBReligion);
        BExperinces=(TextView) findViewById(R.id.txtBExperiences);
        pricePerDay=(TextView) findViewById(R.id.txtBPerDay);
        pricePerHour=(TextView) findViewById(R.id.txtBPerHour);
        ivImage = (ImageView) findViewById(R.id.ivDetails);
        chooseBtn=(Button)findViewById(R.id.btnRate);
        backBtn=(Button) findViewById(R.id.btnBackDetails);

        chooseBtn.setOnClickListener(this);
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

        if(getIntent().getSerializableExtra("babysitter") != null){
            babysitter baby = (babysitter) getIntent().getSerializableExtra("babysitter");

            Picasso.with(this)
                    .load(baby.image)
                    .placeholder(R.drawable.baebishita)
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivImage);
            BName.setText(baby.babysitter_name);
           BPhoneNum.setText(baby.babysitter_phoneNum);
            BAddress.setText(baby.babysitter_address);
            BArea.setText(baby.babysitter_area);
            BReligion.setText(baby.babysitter_religion);
          BExperinces.setText(baby.babysitter_experiences);
            pricePerHour.setText(baby.babysitterPricePerHour);
            pricePerDay.setText(baby.babysitterPricePerDay);

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

                parenthp.setText(id);


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
                Intent profile1 = new Intent(getApplicationContext(), bookingForm.class);
                profile1.putExtra("babysitter_area",BArea.getText().toString());
                profile1.putExtra("babysitter_phoneNum",BPhoneNum.getText().toString());

                startActivity(profile1);
                break;

               /* String url="https://e-baebishita.000webhostapp.com/book.php";

                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);


                                if (jsonResponse.getString("status").equalsIgnoreCase("1")) {
                                    Toast.makeText(getApplicationContext(), "Booking Successful", Toast.LENGTH_LONG).show();
                                    // start profile activity
                                   Intent profile = new Intent(getApplicationContext(), afterLogin.class);
                                    profile.putExtra("user-info", response);
                                    //profile.putExtra("info", response);

                                    startActivity(profile);
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_LONG).show();

                            }

                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();


                        }
                    })
                    {
                        protected Map<String,String> getParams() throws AuthFailureError {
                            Map<String,String> params;

                            params = new HashMap<>();
                            params.put("babysitter_phoneNum",BPhoneNum.getText().toString());
                            params.put("parent_phoneNum", parenthp.getText().toString());


                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);


                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;*/
            case R.id.btnBackDetails:
                Intent profile = new Intent(getApplicationContext(), listBabysitter.class);
                startActivity(profile);
                break;
        }
    }



}

