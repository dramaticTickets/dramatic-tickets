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

public class ChooseMovie  extends AppCompatActivity {
    private ArrayList<Map<String,Object>> MovieData= new ArrayList<Map<String,Object>>();
    private String CinemaName = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_movie);

        final ListView movieList = (ListView) findViewById(R.id.choose_movie_list);
        initMovie();

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            CinemaName = extras.getString("CinemaName");
        }


        movieList.setAdapter(new SimpleAdapter(ChooseMovie.this,MovieData,R.layout.movie_choose_item,
                new String[]{"movie_chinese_name","director_name","main_actor_name"},new int[]{R.id.movie_chinese_name,R.id.director_name,R.id.main_actor_name}));

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = MovieData.get(position);
                Toast.makeText(ChooseMovie.this, "您点击了影院"+CinemaName+"电影名为"+ temp.get("movie_chinese_name").toString(), Toast.LENGTH_SHORT).show();

                //此处可以得到电影名和影院名传出
                Intent intent = new Intent();
                intent.setClass(ChooseMovie.this, ChooseSessionActivity.class);

                intent.putExtra("CinemaName", CinemaName);
                intent.putExtra("MovieName", temp.get("movie_chinese_name").toString());
                ChooseMovie.this.startActivity(intent);
            }
        });
        Toast.makeText(ChooseMovie.this, "11111", Toast.LENGTH_SHORT).show();

    }

    private void initMovie() {//初始化影院信息
        Map<String,Object> item = new HashMap<String,Object>();
        Map<String,Object> item2 = new HashMap<String,Object>();
        Map<String,Object> item3 = new HashMap<String,Object>();
        Map<String,Object> item4 = new HashMap<String,Object>();
        item.put("movie_chinese_name", "头号玩家");
        item.put("director_name", "导演名1");
        item.put("main_actor_name","主演1");
        MovieData.add(item);

        item2.put("movie_chinese_name", "复仇者");
        item2.put("director_name", "导演名2");
        item2.put("main_actor_name","主演2");
        MovieData.add(item2);

        item3.put("movie_chinese_name", "死侍2");
        item3.put("director_name", "导演名3");
        item3.put("main_actor_name","主演3");
        MovieData.add(item3);

        item4.put("movie_chinese_name", "爵迹");
        item4.put("director_name", "导演名4");
        item4.put("main_actor_name","主演4");
        MovieData.add(item4);
    }
}