package com.syafikha.e_baebishita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class term extends AppCompatActivity implements View.OnClickListener  {
    TextView txt1,txt2, txt3;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        txt1=(TextView) findViewById(R.id.text1);
        txt1.setText("The membership fee is RM 10 per year. The initial fee will be taken from your credit/debit card and subsequent payments will be deducted automatically until you give notice to cease membership.");

        txt2=(TextView) findViewById(R.id.text2);
        txt2.setText("There is a non-refundable 50% booking fee each time a babysitter is arranged.");

        txt3=(TextView)findViewById(R.id.text3);
        txt3.setText("The booking rate is 100% depend on the babysitter. If you choose the babysitter to babysit at your house the cost of transforation need to cover by the parent.");



        btnBack=(Button) findViewById(R.id.btnBackTerm);
        btnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBackTerm:
                Intent i = new Intent(getApplication(),afterLogin.class);
                startActivity(i);

        }

    }
}
