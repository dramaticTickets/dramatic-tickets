package com.example.chen.dramatic_tickets.PayActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.dramatic_tickets.LoginAndRegister.SignUpActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.model.User;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConfirmOrder extends AppCompatActivity {
    private String MovieName = null;
    private String CinemaName = null;
    private String tinghaoMessage = null;
    private String startTimeMessage = null;
    private String LeaveTime = null;
    private String TheDate = null;
    private String price = null;
    private String rowCol = null;
    private String phoneNumber;
    private int tinghao;
    private int startTime;
    private int row;
    private int col;
    private int sessionId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);

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

            tinghao = extras.getInt("tinghao");
            startTime = extras.getInt("startTime");
            row = extras.getInt("row");
            col = extras.getInt("col");

            mView = (TextView) findViewById(R.id.movie_chinese_name);
            mView.setText(MovieName);
            mView = (TextView) findViewById(R.id.ticket_date);
            mView.setText("今天"+TheDate);
            mView = (TextView) findViewById(R.id.ticket_time);
            mView.setText(startTimeMessage+"~"+LeaveTime);
            mView = (TextView) findViewById(R.id.ticket_cinema);
            mView.setText(CinemaName);
            mView = (TextView) findViewById(R.id.ticket_hall);
            mView.setText(tinghaoMessage);
            mView = (TextView) findViewById(R.id.ticket_seats);
            mView.setText(rowCol);
            mView = (TextView) findViewById(R.id.pay_amount);
            mView.setText(price);
        }

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);

        if(MovieName.equals("头号玩家")) {
            imageView.setImageResource(R.mipmap.poster_small_player);
        }
        if(MovieName.equals("复仇者联盟")) {
            imageView.setImageResource(R.mipmap.poster_small_avengers);
        }
        if(MovieName.equals("死侍")) {
            imageView.setImageResource(R.mipmap.poster_small_deadpool);
        }
        if(MovieName.equals("爵迹")) {
            imageView.setImageResource(R.mipmap.poster_small_jueji);
        }

        get_session_id();

        Button btn = (Button) findViewById(R.id.buy_tickets_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ConfirmOrder.this, PaymentSuccess.class);

                intent.putExtra("MovieName", MovieName);
                intent.putExtra("CinemaName", CinemaName);
                intent.putExtra("tinghaoMessage", tinghaoMessage);
                intent.putExtra("startTimeMessage", startTimeMessage);
                intent.putExtra("LeaveTime", LeaveTime);
                intent.putExtra("TheDate", TheDate);
                intent.putExtra("rowCol", rowCol);
                intent.putExtra("price", price);

                intent.putExtra("row", row);
                intent.putExtra("col", col);
                intent.putExtra("sessionId", sessionId);

                ConfirmOrder.this.startActivity(intent);
            }
        });
    }

    public void get_session_id() {
        DramaticService service = ServiceFactory.createService(DramaticService.class);
        service.getSessionId(CinemaName, tinghao, 20180630, startTime)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieSession>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieSession session) {
                        sessionId = session.getSessionId();
                    }
                });

    }
}
