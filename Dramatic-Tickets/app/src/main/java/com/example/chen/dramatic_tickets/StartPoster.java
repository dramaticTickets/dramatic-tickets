package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.dramatic_tickets.MainPage.HomeActivity;

public class StartPoster extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 两秒后进入系统

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_poster);

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(StartPoster.this, HomeActivity.class);
                StartPoster.this.startActivity(mainIntent);
                StartPoster.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);

    }
}