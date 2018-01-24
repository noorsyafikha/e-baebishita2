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

public class profile extends AppCompatActivity implements View.OnClickListener {
    TextView name, phone,password, address, religion;
    Button backBtn,updateBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;
    RequestQueue requestQueue;
    String uname,phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=(TextView) findViewById(R.id.txtParentName);
        phone=(TextView) findViewById(R.id.txtParentPhoneNum);
        password=(TextView) findViewById(R.id.txtParentPassword);
        address=(TextView) findViewById(R.id.txtParentAddress);
        religion=(TextView)findViewById(R.id.txtParentReligion);
        backBtn=(Button) findViewById(R.id.btnBackProfile);
        updateBtn=(Button) findViewById(R.id.btnUpdate1);

        backBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // process
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
    }

    private void viewData() {

        String url = "https://e-baebishita.000webhostapp.com/viewProfile.php";
        StringRequest dataRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                    String name1, phone1, password1, address1, religion1;

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
                        religion1 = data.getString("parent_religion").toString();

                 } catch (Exception e) { e.printStackTrace(); }

                name.setText(name1);
                phone.setText(phone1);
                password.setText(password1);
                address.setText(address1);
                religion.setText(religion1);

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
        switch (v.getId())
        {
            case R.id.btnBackProfile:
                Intent i = new Intent(getApplication(),afterLogin.class);
                startActivity(i);
                break;

            case  R.id.btnUpdate1:
                i = new Intent(getApplication(),updateProfile.class);
                startActivity(i);
                break;
        }

    }
}
