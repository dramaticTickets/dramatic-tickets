package com.example.chen.dramatic_tickets.ChooseActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.LoginAndRegister.SignInActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.Movie;
import com.example.chen.dramatic_tickets.model.User;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetail extends AppCompatActivity {

    String selectedMovieName = null;
    TextView mView;
    private String phoneNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            selectedMovieName = extras.getString("selectedMovieName");
            phoneNumber = extras.getString("phoneNumber");
            mView = (TextView) findViewById(R.id.movie_chinese_name);
            mView.setText(selectedMovieName);

            mView = (TextView) findViewById(R.id.movie_country);
            if(selectedMovieName.equals("爵迹")) {
                mView.setText("中国");
            }

            mView = (TextView) findViewById(R.id.movie_onDate);
            if(selectedMovieName.equals("头号玩家")) {
                mView.setText("2018年03月30日上映");
            }
            if(selectedMovieName.equals("复仇者联盟")) {
                mView.setText("2012年05月11日上映");
            }
            if(selectedMovieName.equals("死侍")) {
                mView.setText("2012年05月18日上映");
            }
            if(selectedMovieName.equals("爵迹")) {
                mView.setText("2016年09月29日上映");
            }

            ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
            if(selectedMovieName.equals("头号玩家")) {
                imageView.setImageResource(R.mipmap.poster_small_player);
            }
            if(selectedMovieName.equals("复仇者联盟")) {
                imageView.setImageResource(R.mipmap.poster_small_avengers);
            }
            if(selectedMovieName.equals("死侍")) {
                imageView.setImageResource(R.mipmap.poster_small_deadpool);
            }
            if(selectedMovieName.equals("爵迹")) {
                imageView.setImageResource(R.mipmap.poster_small_jueji);
            }
        }

        DramaticService service = ServiceFactory.createService(DramaticService.class);
        service.getMovie(selectedMovieName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {             //HTTP错误
                            HttpException httpException = (HttpException) e;
                            Toast.makeText(MovieDetail.this, ((HttpException) e).code()+"", Toast.LENGTH_SHORT).show();
                            }
                        //success = false;
                    }

                    @Override
                    public void onNext(Movie movie) {

                        mView = (TextView) findViewById(R.id.movie_english_name);
                        mView.setText(movie.getEnglishName());
                        mView = (TextView) findViewById(R.id.movie_type);
                        mView.setText(movie.getMovieType());

                        mView = (TextView) findViewById(R.id.movie_length);
                        mView.setText(movie.getLength()+"分钟");

                        mView = (TextView) findViewById(R.id.moive_introduction);
                        mView.setText(movie.getIntroduction());
                        //success = true;

                    }
                });
        Button btn = (Button) findViewById(R.id.buyTicketInMovieDetail);
        clickBuyTicket(btn);
    }

    //这里跳转到选择影院界面
    public void clickBuyTicket(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MovieDetail.this, ChooseCinema.class);

                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("MovieName", selectedMovieName);
                MovieDetail.this.startActivity(intent);
            }
        });
    }
}