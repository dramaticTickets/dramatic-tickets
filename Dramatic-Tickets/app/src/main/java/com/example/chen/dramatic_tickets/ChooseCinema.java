package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseCinema extends AppCompatActivity {
    private ArrayList<Map<String,Object>> cinemaData= new ArrayList<Map<String,Object>>();
    private String MovieName = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_cinema);

        final ListView cinemaList = (ListView) findViewById(R.id.choose_cinema_list);
        initCinema();

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
        }

        cinemaList.setAdapter(new SimpleAdapter(ChooseCinema.this,cinemaData,R.layout.cinema_item,
                new String[]{"name","cinemaAddress","minPrice"},new int[]{R.id.name,R.id.cinemaAddress,R.id.minPrice}));
        cinemaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = cinemaData.get(position);
                Toast.makeText(ChooseCinema.this, "您点击了影院"+ temp.get("name").toString()+"电影名为"+MovieName, Toast.LENGTH_SHORT).show();

                //此处可以得到电影名和影院名传出
                Intent intent = new Intent();
                intent.setClass(ChooseCinema.this, ChooseSessionActivity.class);

                intent.putExtra("MovieName", MovieName);
                intent.putExtra("CinemaName", temp.get("name").toString());
                ChooseCinema.this.startActivity(intent);
            }
        });

    }

    private void initCinema() {//初始化影院信息
        Map<String,Object> item = new HashMap<String,Object>();
        Map<String,Object> item2 = new HashMap<String,Object>();
        Map<String,Object> item3 = new HashMap<String,Object>();
        Map<String,Object> item4 = new HashMap<String,Object>();
        Map<String,Object> item5 = new HashMap<String,Object>();
        item.put("name", "金逸珠江国际影城广州大学城店");
        item.put("cinemaAddress", "番禺区小谷街道贝岗村中二横路1号GOGO新天地商业广场二期二楼");
        item.put("minPrice","34元");
        cinemaData.add(item);

        item2.put("name", "广东科学中心巨幕影院");
        item2.put("cinemaAddress", "番禺区广州大学城西六路168号");
        item2.put("minPrice","29.9元");
        cinemaData.add(item2);

        item3.put("name", "广州万达影城万胜广场店");
        item3.put("cinemaAddress", "海珠区新港东路1236号万胜广场五层");
        item3.put("minPrice","24.9元");
        cinemaData.add(item3);

        item4.put("name", "星河影城番禺南村店");
        item4.put("cinemaAddress", "番禺区番禺南村兴业大道之一人人佳购物广场2楼");
        item4.put("minPrice","32.9元");
        cinemaData.add(item4);

        item5.put("name", "广州hmv映联万和国际影城(南丰汇店)");
        item5.put("cinemaAddress", "海珠区新港东路618号南丰汇广场3楼");
        item5.put("minPrice","29.9元");
        cinemaData.add(item5);
    }
}