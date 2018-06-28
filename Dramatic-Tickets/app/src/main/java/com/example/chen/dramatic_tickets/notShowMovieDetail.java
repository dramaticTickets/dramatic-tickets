package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class notShowMovieDetail extends AppCompatActivity {

    String selectedMovieName = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            selectedMovieName = extras.getString("selectedMovieName");
            mView = (TextView) findViewById(R.id.movie_chinese_name);
            mView.setText(selectedMovieName);
        }

        Button btn = (Button) findViewById(R.id.buyTicketInMovieDetail);
        btn.setText("尚未上映");
        btn.setBackgroundColor(Color.parseColor("#d0d0d0"));
    }
}
