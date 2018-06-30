package com.example.chen.dramatic_tickets.ChooseActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.Movie;
import com.example.chen.dramatic_tickets.service.DramaticService;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class notShowMovieDetail extends AppCompatActivity {

    String selectedMovieName = null;
    TextView mView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            selectedMovieName = extras.getString("selectedMovieName");
            mView = (TextView) findViewById(R.id.movie_chinese_name);
            mView.setText(selectedMovieName);
        }

        mView = (TextView) findViewById(R.id.movie_onDate);
        if(selectedMovieName.equals("金银岛")) {
            mView.setText("2012年05月05日上映");
        }
        if(selectedMovieName.equals("超人总动员")) {
            mView.setText("2004年11月05日上映");
        }
        if(selectedMovieName.equals("侏罗纪世界2")) {
            mView.setText("2018年06月15日上映");
        }
        if(selectedMovieName.equals("深海越狱")) {
            mView.setText("2018年06月08日上映");
        }

        ImageView imageView = (ImageView) findViewById(R.id.movie_poster);
        if(selectedMovieName.equals("金银岛")) {
            imageView.setImageResource(R.mipmap.poster_small_island);
        }
        if(selectedMovieName.equals("超人总动员")) {
            imageView.setImageResource(R.mipmap.poster_small_super);
        }
        if(selectedMovieName.equals("侏罗纪世界2")) {
            imageView.setImageResource(R.mipmap.poster_small_dino);
        }
        if(selectedMovieName.equals("深海越狱")) {
            imageView.setImageResource(R.mipmap.poster_small_jail);
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
                            Toast.makeText(notShowMovieDetail.this, ((HttpException) e).code()+"", Toast.LENGTH_SHORT).show();
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
        btn.setText("尚未上映");
        btn.setBackgroundColor(Color.parseColor("#d0d0d0"));
    }
}
