package com.example.chen.dramatic_tickets.PersonalHistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.chen.dramatic_tickets.R;

/**
 * Created by Admin on 2018/6/29.
 */

public class PersonalTicketDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //if(extras != null) {
            String movieName = extras.getString("movie_chinese_name");
            String ticket_date = extras.getString("ticket_date");
            String ticket_time = extras.getString("ticket_time");
            String ticket_cinema = extras.getString("ticket_cinema");
            String price = extras.getString("price");
            String hallNum = extras.getString("hallNum");
            String seatRow = extras.getString("seatRow");
            String seatCol = extras.getString("seatCol");
            int ticketId = extras.getInt("ticketId");
       // }
        TextView mView;
        mView = (TextView) findViewById(R.id.movie_chinese_name);
        mView.setText(movieName);
        mView = (TextView) findViewById(R.id.ticket_time);
        mView.setText(ticket_time);
        mView = (TextView) findViewById(R.id.ticket_date);
        mView.setText(ticket_date);
        mView = (TextView) findViewById(R.id.ticket_cinema);
        mView.setText(ticket_cinema);
        mView = (TextView) findViewById(R.id.ticket_hall);
        mView.setText(hallNum+"号厅");
        mView = (TextView) findViewById(R.id.ticket_seats);
        mView.setText(seatRow+"排"+seatCol+"座");
        mView = (TextView) findViewById(R.id.pay_amount);
        mView.setText(price+"元");
        mView = (TextView) findViewById(R.id.ticket_serial_number);
        mView.setText(ticketId+"");
        mView = (TextView) findViewById(R.id.ticket_verify_number);
        mView.setText(ticketId*7%1000000+"");

    }
}
