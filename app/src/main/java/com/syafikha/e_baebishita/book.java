package com.syafikha.e_baebishita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class book extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner areaSpinner;
    Button searchBtn,backBtn;
    RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;
    String uname,phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        areaSpinner=(Spinner) findViewById(R.id.spinnerArea);
        areaSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.area_spinner, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        areaSpinner.setAdapter(adapter);


        searchBtn=(Button) findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(this);

       backBtn=(Button) findViewById(R.id.btnBackBook);
       backBtn.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE); // shared preference is used to maintain login session
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

    }

    @Override
    public void onItemSelected
            (AdapterView<?> parent, View view, int position, long id) {

      /*Toast.makeText(parent.getContext(),
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
            case R.id.btnSearch:
               Intent i=new Intent (getApplicationContext(), listBabysitter.class);
                i.putExtra("area",areaSpinner.getSelectedItem().toString());
                startActivity(i);
                break;


            case R.id.btnBackBook:
                Intent profile = new Intent(getApplicationContext(), afterLogin.class);
                startActivity(profile);
                break;
        }
    }
}
