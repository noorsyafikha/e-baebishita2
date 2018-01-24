package com.syafikha.e_baebishita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FAQ extends AppCompatActivity implements View.OnClickListener{
    TextView txt1,txt2,txt3,txt4,txt5,txt6;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        txt1=(TextView) findViewById(R.id.text1);
        txt1.setText("Q: How do I know that I am getting a good, safe and qualified sitter?");

        txt2=(TextView) findViewById(R.id.text2);
        txt2.setText("A: By viewing the rating and experiences of the babysitter");

        txt3=(TextView) findViewById(R.id.text3);
        txt3.setText("Q: When should I book the babysitter?");

        txt4=(TextView) findViewById(R.id.text4);
        txt4.setText("A: It is always a good idea to book your reservation with as much notice as possible. Booking a sitter with at least 24 hours notice is suggested. last-minute reservations are based on availability.");

        txt5=(TextView) findViewById(R.id.text5);
        txt5.setText("Q: How does payment works?");

        txt6=(TextView) findViewById(R.id.text6);
        txt6.setText("A: You need to pay directly to the babysitter");

        backBtn=(Button) findViewById(R.id.btnBackFAQ);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBackFAQ:
                Intent i = new Intent(getApplication(),afterLogin.class);
                startActivity(i);

        }

    }
}
