package com.example.chen.dramatic_tickets.PayActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chen.dramatic_tickets.LoginAndRegister.SignUpActivity;
import com.example.chen.dramatic_tickets.MainPage.HomeActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.Ticket;
import com.example.chen.dramatic_tickets.model.User;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PaymentSuccess extends AppCompatActivity {
    private String MovieName = null;
    private String CinemaName = null;
    private String tinghaoMessage = null;
    private String startTimeMessage = null;
    private String LeaveTime = null;
    private String TheDate = null;
    private String price = null;
    private String rowCol = null;

    private int row;
    private int col;
    private int sessionId;

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

            row = extras.getInt("row");
            col = extras.getInt("col");
            sessionId = extras.getInt("sessionId");

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

        buyTicket();

        Button btn = (Button) findViewById(R.id.back_to_home);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PaymentSuccess.this, HomeActivity.class);
                PaymentSuccess.this.startActivity(intent);
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


            }
        });
    }

    public void buyTicket() {
        DramaticService service = ServiceFactory.createService(DramaticService.class);
        Map<String, Object> temp = new HashMap<>();
        temp.put("cinemaName", CinemaName);
        temp.put("sessionId", sessionId);
        temp.put("phone", "13719341638");
        temp.put("seatRow", row+1);
        temp.put("seatCol", col+1);

        service.bookTicket(temp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ticket>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {             //HTTP错误
                            HttpException httpException = (HttpException) e;
                            if(httpException.code() == 408) {
                                //Toast.makeText(PaymentSuccess.this, "seat is not empty", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //success = false;
                    }

                    @Override
                    public void onNext(Ticket ticket) {

                    }
                });

    }
}
