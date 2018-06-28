package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class PaymentSuccess extends AppCompatActivity {
    private String MovieName = null;
    private String CinemaName = null;
    private String tinghaoMessage = null;
    private String startTimeMessage = null;
    private String LeaveTime = null;
    private String TheDate = null;
    private String price = null;
    private String rowCol = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_succeed);

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
            CinemaName = extras.getString("CinemaName");
            tinghaoMessage = extras.getString("tinghaoMessage");
            startTimeMessage = extras.getString("startTimeMessage");
            TheDate = extras.getString("TheDate");
            price = extras.getString("price");
            LeaveTime = extras.getString("LeaveTime");
            rowCol = extras.getString("rowCol");

            mView = (TextView) findViewById(R.id.movie_chinese_name);
            mView.setText(MovieName);
            mView = (TextView) findViewById(R.id.ticket_time);
            mView.setText(startTimeMessage);
            mView = (TextView) findViewById(R.id.ticket_date);
            mView.setText("2018年"+TheDate);
            mView = (TextView) findViewById(R.id.ticket_cinema);
            mView.setText(CinemaName);
            mView = (TextView) findViewById(R.id.ticket_hall);
            mView.setText(tinghaoMessage);
            mView = (TextView) findViewById(R.id.ticket_seats);
            mView.setText(rowCol);
            mView = (TextView) findViewById(R.id.payment_amount);
            mView.setText(price);

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);;
            int minute = c.get(Calendar.MINUTE);

            mView = (TextView) findViewById(R.id.payment_time);
            mView.setText(year+"-"+"0"+(month+1)+"-"+day+ "  "+hour+":"+minute);
        }

        Button btn = (Button) findViewById(R.id.back_to_home);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里需要将数据传给数据库
                /*
                intent.putExtra("MovieName", MovieName);
                intent.putExtra("CinemaName", CinemaName);
                intent.putExtra("tinghaoMessage", tinghaoMessage);
                intent.putExtra("startTimeMessage", startTimeMessage);
                intent.putExtra("LeaveTime", LeaveTime);
                intent.putExtra("TheDate", TheDate);
                intent.putExtra("rowCol", rowCol);
                intent.putExtra("price", price);*/

                Intent intent = new Intent();
                intent.setClass(PaymentSuccess.this, HomeActivity.class);


                PaymentSuccess.this.startActivity(intent);
            }
        });
    }
}
