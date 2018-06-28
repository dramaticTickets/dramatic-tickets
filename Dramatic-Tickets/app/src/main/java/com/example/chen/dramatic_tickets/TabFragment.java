package com.example.chen.dramatic_tickets;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chen on 2018/6/27.
 */

public class TabFragment extends Fragment {

    private String MovieName = null;
    private String CinemaName = null;

    List<Map<String, Object>> data = new ArrayList<>();

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
        }

        view = inflater.inflate(R.layout.choose_session_date, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        String[] startTime = new String[] {"10:20", "10:30", "10:40"};
        String[] LeaveTime = new String[] {"12:46", "12:46", "12:46"};
        String[] tinghao = new String[] {"1号厅", "2号厅", "3号厅"};
        String[] price = new String[] {"35元", "35元", "36元"};

        for(int i = 0; i < 3; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("startTime", startTime[i]);
            temp.put("LeaveTime", LeaveTime[i]);
            temp.put("tinghao", tinghao[i]);
            temp.put("price", price[i]);
            data.add(temp);
        }

        final MyAdapter myAdapter = new MyAdapter(getActivity(), R.layout.choose_session_list, data) {
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

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH) + 1;
        final int day = c.get(Calendar.DAY_OF_MONTH);



        if (tag == "jintian") {

            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {
                    Toast.makeText(getActivity(), ""+position+"jintian", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = data.get(position);
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

                    intent.putExtra("Date", month+"月"+day+"日");

                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongClick(int position)
                {
                }
            });
        } else if (tag == "mingtian") {
            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {
                    Toast.makeText(getActivity(), ""+position+"mingtian", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = data.get(position);
                    String startTimeMessage = s.get("startTime").toString();
                    String tinghaoMessage = s.get("tinghao").toString();

                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);
                    intent.putExtra("Date", "今天"+month+"月"+(day+1)+"日");

                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongClick(int position)
                {
                }
            });
        } else {
            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

                @Override
                public void onClick(int position)
                {
                    Toast.makeText(getActivity(), ""+position+"houtian", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ChooseSeatActivity.class);

                    Map<String, Object> s = data.get(position);
                    String startTimeMessage = s.get("startTime").toString();
                    String tinghaoMessage = s.get("tinghao").toString();

                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);
                    intent.putExtra("Date", "今天"+month+"月"+(day+2)+"日");

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
}
