package com.example.chen.dramatic_tickets.ChooseSession;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.Adapter.MyAdapter;
import com.example.chen.dramatic_tickets.ChooseActivity.ChooseCinema;
import com.example.chen.dramatic_tickets.ChooseSeat.ChooseSeatActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.Cinema;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chen on 2018/6/27.
 */

public class TabFragment extends Fragment {

    private String MovieName = null;
    private String CinemaName = null;
    private int date[];
    private int startTime[];
    private int tinghao[];
    private String price[];


    private String[] startTimeD = new String[10];
    private String[] LeaveTimeD= new String[10];
    private String[] tinghaoD= new String[10];
    private String[] priceD= new String[10];

    private MyAdapter myAdapter;
    List<Map<String, Object>> dataNow = new ArrayList<>();

    public static TabFragment newInstance(String info) {
        Bundle args = new Bundle();
        TabFragment fragment = new TabFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String tag = getArguments().getString("info");
        View view = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
            CinemaName = extras.getString("CinemaName");
            date = extras.getIntArray("date");
            startTime = extras.getIntArray("startTime");
            tinghao = extras.getIntArray("tinghao");
            price = extras.getStringArray("price");
        }

        view = inflater.inflate(R.layout.choose_session_date, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        for(int i = 0; i < date.length; i++) {
            if(startTime[i]%100 < 10) {
                startTimeD[i] = ((int)startTime[i]/100) +":"+"0"+(startTime[i]%100);
            } else {
                startTimeD[i] = ((int)startTime[i]/100) +":"+(startTime[i]%100);
            }
            LeaveTimeD[i] = (((int)startTime[i]/100)+2)%24 +":"+((startTime[i]%100)+26)%60;
            tinghaoD[i] = tinghao[i]+"号厅";
            priceD[i] = price[i];
        }
        final int length = date.length;


        /*
        String[] startTimeD = new String[] {"10:20", "10:30", "10:40"};
        String[] LeaveTimeD = new String[] {"12:46", "12:46", "12:46"};
        String[] tinghaoD = new String[] {"1号厅", "2号厅", "3号厅"};
        String[] priceD = new String[] {"35元", "35元", "36元"};

        for(int i = 0; i < 3; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("startTime", startTime[i]);
            temp.put("LeaveTime", LeaveTime[i]);
            temp.put("tinghao", tinghao[i]);
            temp.put("price", price[i]);
            data.add(temp);
        }*/

        myAdapter = new MyAdapter(getActivity(), R.layout.choose_session_list, dataNow) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView movie_start_time = holder.getView(R.id.movie_start_time);
                movie_start_time.setText(s.get("startTime").toString());
                TextView movie_end_time = holder.getView(R.id.movie_end_time);
                movie_end_time.setText(s.get("LeaveTime").toString()+"散场");
                TextView movie_hall = holder.getView(R.id.movie_hall);
                movie_hall.setText(s.get("tinghao").toString());
                TextView price = holder.getView(R.id.price);
                price.setText(s.get("price").toString());
            }
        };


        recyclerView.setAdapter(myAdapter);

        //获得日期
        Date date_ = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(date_);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        Calendar hotian = Calendar.getInstance();
        hotian.add(Calendar.DATE, 2);

        final int todayI = today.get(Calendar.YEAR) * 10000 + (today.get(Calendar.MONTH) + 1) * 100 + today.get(Calendar.DAY_OF_MONTH);
        final int mingtianI = tomorrow.get(Calendar.YEAR) * 10000 + (tomorrow.get(Calendar.MONTH) + 1) * 100 + tomorrow.get(Calendar.DAY_OF_MONTH);
        final int houtianI = hotian.get(Calendar.YEAR) * 10000 + (hotian.get(Calendar.MONTH) + 1) * 100 + hotian.get(Calendar.DAY_OF_MONTH);

        final String todayS = (today.get(Calendar.MONTH) + 1)+"月" + today.get(Calendar.DAY_OF_MONTH) + "日";
        final String mingtianS = (tomorrow.get(Calendar.MONTH) + 1)+"月" + tomorrow.get(Calendar.DAY_OF_MONTH) + "日";
        final String houtianS = (hotian.get(Calendar.MONTH) + 1)+"月" + hotian.get(Calendar.DAY_OF_MONTH) + "日";

        if (tag == "jintian") {
            for(int i = 0; i < dataNow.size(); i++) {
                dataNow.remove(0);
            }
            for(int i= 0; i < length; i++) {
                if(todayI == date[i]) {
                    Map<String, Object> temp = new LinkedHashMap<>();
                    temp.put("startTime", startTimeD[i]);
                    temp.put("LeaveTime", LeaveTimeD[i]);
                    temp.put("tinghao", tinghaoD[i]);
                    temp.put("price", priceD[i]);
                    dataNow.add(temp);
                    myAdapter.notifyDataSetChanged();
                }
            }

            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = dataNow.get(position);
                    String startTimeMessage = s.get("startTime").toString();
                    String tinghaoMessage = s.get("tinghao").toString();
                    String price = s.get("price").toString();
                    String LeaveTime = s.get("LeaveTime").toString();

                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);

                    intent.putExtra("price", price);
                    intent.putExtra("LeaveTime", LeaveTime);

                    intent.putExtra("Date", todayS);
                    intent.putExtra("startTime", startTime[position]);

                    intent.putExtra("tinghao", tinghao[position]);

                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongClick(int position)
                {
                }
            });
        } else if (tag == "mingtian") {
            for(int i = 0; i < dataNow.size(); i++) {
                dataNow.remove(0);
            }
            for(int i= 0; i < length; i++) {
                if(mingtianI == date[i]) {
                    Map<String, Object> temp = new LinkedHashMap<>();
                    temp.put("startTime", startTimeD[i]);
                    temp.put("LeaveTime", LeaveTimeD[i]);
                    temp.put("tinghao", tinghaoD[i]);
                    temp.put("price", priceD[i]);
                    dataNow.add(temp);
                    myAdapter.notifyDataSetChanged();
                }
            }

            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = dataNow.get(position);
                    String startTimeMessage = s.get("startTime").toString();
                    String tinghaoMessage = s.get("tinghao").toString();

                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);
                    intent.putExtra("Date", mingtianS);
                    intent.putExtra("startTime", startTime[position]);
                    intent.putExtra("tinghao", tinghao[position]);

                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongClick(int position)
                {
                }
            });
        } else {
            for(int i = 0; i < dataNow.size(); i++) {
                dataNow.remove(0);
            }
            for(int i= 0; i < length; i++) {
                if(houtianI == date[i]) {
                    Map<String, Object> temp = new LinkedHashMap<>();
                    temp.put("startTime", startTimeD[i]);
                    temp.put("LeaveTime", LeaveTimeD[i]);
                    temp.put("tinghao", tinghaoD[i]);
                    temp.put("price", priceD[i]);
                    dataNow.add(temp);
                    myAdapter.notifyDataSetChanged();
                }
            }
            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = dataNow.get(position);
                    String startTimeMessage = s.get("startTime").toString();
                    String tinghaoMessage = s.get("tinghao").toString();

                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);
                    intent.putExtra("Date", houtianS);
                    intent.putExtra("startTime", startTime[position]);
                    intent.putExtra("tinghao", tinghao[position]);

                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongClick(int position)
                {
                }
            });
        }


        return view;
    }

    private void initData() {//初始化影院信息
       /* DramaticService service = ServiceFactory.createService(DramaticService.class);
        service.getSessions(CinemaName, MovieName)
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
                            Toast.makeText(getActivity(), ((HttpException) e).code()+"", Toast.LENGTH_SHORT).show();
                        }
                        //success = false;
                    }

                    @Override
                    public void onNext(MovieSession movieSession[]) {

                        for(int i = 0; i <movieSession.length; i++) {

                            int date = movieSession[i].getDate();
                            int startTime = movieSession[i].getStartTime();
                            int tinghao = movieSession[i].getHallNum();
                            String price = movieSession[i].getPrice() + "元";



                            Map<String, Object> temp = new HashMap<String, Object>();
                            temp.put("Date", date);
                            temp.put("startTime", startTime);
                            temp.put("tinghao", tinghao);
                            temp.put("price", price);
                            data.add(temp);
                        }
                        //success = true;

                    }
                });*/
    }
}
