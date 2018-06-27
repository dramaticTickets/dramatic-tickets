package com.example.chen.dramatic_tickets;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MovieDetail extends AppCompatActivity {

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
    }
}