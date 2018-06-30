package com.example.chen.dramatic_tickets.ChooseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.ChooseSession.ChooseSessionActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.Cinema;
import com.example.chen.dramatic_tickets.model.Movie;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseCinema extends AppCompatActivity {
    private ArrayList<Map<String,Object>> cinemaData= new ArrayList<Map<String,Object>>();
    private String MovieName = null;
    TextView mView;
    private SimpleAdapter simpleAdapter;
    private String phoneNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_cinema);

        final ListView cinemaList = (ListView) findViewById(R.id.choose_cinema_list);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
            phoneNumber = extras.getString("phoneNumber");
        }

        simpleAdapter = new SimpleAdapter(ChooseCinema.this,cinemaData,R.layout.cinema_item,
                new String[]{"name","cinemaAddress","minPrice"},new int[]{R.id.name,R.id.cinemaAddress,R.id.minPrice});

        cinemaList.setAdapter(simpleAdapter);

        initCinema();
        cinemaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = cinemaData.get(position);

                final String CinemaName = temp.get("name").toString();
                //此处可以得到电影名和影院名传出
                DramaticService service = ServiceFactory.createService(DramaticService.class);
                service.getSessions(temp.get("name").toString(), MovieName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<MovieSession[]>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {             //HTTP错误
                                    HttpException httpException = (HttpException) e;
                                    Toast.makeText(ChooseCinema.this, "该电影尚未排期", Toast.LENGTH_SHORT).show();
                                }
                                //success = false;
                            }

                            @Override
                            public void onNext(MovieSession movieSession[]) {
                                int date[] = new int[movieSession.length];
                                int startTime[] = new int[movieSession.length];
                                int tinghao[] = new int[movieSession.length];
                                String price[] = new String[movieSession.length];

                                for(int i = 0; i <movieSession.length; i++) {

                                    date[i] = movieSession[i].getDate();
                                    startTime[i] = movieSession[i].getStartTime();
                                    tinghao[i] = movieSession[i].getHallNum();
                                    price[i] = movieSession[i].getPrice() + "元";

                                }

                                Intent intent = new Intent();
                                intent.setClass(ChooseCinema.this, ChooseSessionActivity.class);

                                intent.putExtra("MovieName", MovieName);
                                intent.putExtra("CinemaName", CinemaName);
                                intent.putExtra("date", date);
                                intent.putExtra("startTime", startTime);
                                intent.putExtra("tinghao", tinghao);
                                intent.putExtra("price", price);

                                intent.putExtra("phoneNumber", phoneNumber);
                                ChooseCinema.this.startActivity(intent);
                                //success = true;

                            }
                        });
            }
        });

    }

    private void initCinema() {//初始化影院信息
        DramaticService service = ServiceFactory.createService(DramaticService.class);
        service.getAllCinema()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cinema[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {             //HTTP错误
                            HttpException httpException = (HttpException) e;
                            Toast.makeText(ChooseCinema.this, ((HttpException) e).code()+"", Toast.LENGTH_SHORT).show();
                        }
                        //success = false;
                    }

                    @Override
                    public void onNext(Cinema cinema[]) {

                        for(int i = 0; i <cinema.length; i++) {

                            String name = cinema[i].getCinemaName();
                            String cinemaAddress = cinema[i].getAddress();
                            String minPrice = cinema[i].getLowestPrice()+"元";

                            Map<String,Object> temp = new HashMap<String,Object>();
                            temp.put("name", name);
                            temp.put("cinemaAddress", cinemaAddress);
                            temp.put("minPrice",minPrice);
                            cinemaData.add(temp);
                            simpleAdapter.notifyDataSetChanged();
                        }
                        //success = true;

                    }
                });
    }
}