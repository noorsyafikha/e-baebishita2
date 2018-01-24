package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class bookingForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Button backBtn,bookBtn;
    EditText dateS, dateE,timeS,timeE,additional;
    TextView babysitterPhone,area;
    Spinner packagesSpinner,locSpinner,numChildSpinner,specialSpinner;
    String uname,phoneNum;

    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        babysitterPhone=(TextView) findViewById(R.id.txtBabysitterHp);
        area=(TextView) findViewById(R.id.txtArea);
        dateS=(EditText)findViewById(R.id.edtDateStart);
        dateE=(EditText) findViewById(R.id.edtDateEnd);
        timeS=(EditText) findViewById(R.id.edtTimeStart);
        timeE=(EditText) findViewById(R.id.edtTimeEnd);
        additional=(EditText) findViewById(R.id.edtAdditional);

        String getArea=getIntent().getStringExtra("babysitter_area");
        String getBabysitterHP=getIntent().getStringExtra("babysitter_phoneNum");

        area.setText(getArea);
        babysitterPhone.setText(getBabysitterHP);

        backBtn=(Button) findViewById(R.id.btnBacForm);
        bookBtn=(Button) findViewById(R.id.btnBook);

        backBtn.setOnClickListener(this);
        bookBtn.setOnClickListener(this);

        packagesSpinner=(Spinner) findViewById(R.id.spinnerPackages);
        packagesSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.packages_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        packagesSpinner.setAdapter(adapter);

        locSpinner=(Spinner) findViewById(R.id.spinnerLoc);
        locSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.location_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
      locSpinner.setAdapter(adapter1);

        numChildSpinner=(Spinner) findViewById(R.id.spinnerNumChild);
        numChildSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.numChild_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        numChildSpinner.setAdapter(adapter2);

        specialSpinner=(Spinner) findViewById(R.id.spinnerSpecial);
        specialSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.special_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        specialSpinner.setAdapter(adapter3);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE); // shared preference is used to maintain login session
        shEditor = sharedPreferences.edit();

        if (sharedPreferences.contains("user-info")) { // check if there are still login session
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                uname = info.getString("parent_name");
                phoneNum = info.getString("parent_phoneNum");
                //babysitterNum=info.getString("babysitter_phoneNum");
                //area=info.getString("area");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void onItemSelected
            (AdapterView<?> parent, View view, int position, long id) {

       /* Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " +
                        parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBook:
                String url;

              url="https://e-baebishita.000webhostapp.com/book.php";

                try {
                    StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // JSONObject jsonResponse = new JSONObject(response);
                            // boolean error = jsonResponse.getBoolean("error");
                            try {
                                if(!response.isEmpty())
                                {

                                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    // start profile activity
                                    Intent profile = new Intent(getApplicationContext(), afterLogin.class);
                                    profile.putExtra("info", response);
                                    Toast.makeText(getApplicationContext(), "Sucessful", Toast.LENGTH_LONG).show();
                                    startActivity(profile);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show(); // show error message
                            } catch (Exception e) { e.printStackTrace(); }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();


                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params;

                            params = new HashMap<>();

                            params.put("numOfChild", String.valueOf(numChildSpinner.getSelectedItem()));
                            params.put("specialNeed", String.valueOf(specialSpinner.getSelectedItem()));
                            params.put("packages", String.valueOf(packagesSpinner.getSelectedItem()));
                            params.put("location", String.valueOf(locSpinner.getSelectedItem()));
                            params.put("dateStart",dateS.getText().toString());
                            params.put("dateEnd",dateE.getText().toString());
                            params.put("timeStart",timeS.getText().toString());
                            params.put("timeEnd",timeE.getText().toString());
                            params.put("additionalInfo",additional.getText().toString());
                            params.put("parent_phoneNum",phoneNum);
                            params.put("babysitter_phoneNum",babysitterPhone.getText().toString());
                           params.put("area",area.getText().toString());


                            return params;
                        }
                    };

                    requestQueue.add(loginRequest);
                    //progressDialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


            case R.id.btnBacForm:
                Intent profile = new Intent(getApplicationContext(), bookingDetails.class);
                startActivity(profile);
                break;
        }
    }
}

