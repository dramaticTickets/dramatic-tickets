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
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/6/29.
 */

public class MovieListActivity extends AppCompatActivity {

    private ArrayList<Map<String,Object>> MovieData= new ArrayList();
    private String movieName = null;

    @Override
    protected void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.search_movies_list);

        TextView movieInfo;

        final ListView movieList = (ListView) findViewById(R.id.tickets_list);
        initMovie();

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            movieName = extras.getString("movieName");
            //检查数据库中相同部分unfinished

        }


        movieList.setAdapter(new SimpleAdapter(MovieListActivity.this,MovieData,R.layout.search_moive_item,
                new String[]{"movie_chinese_name","director_name","main_actor_name"},new int[]{R.id.movie_chinese_name,R.id.director_name,R.id.main_actor_name}));

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = MovieData.get(position);
                Toast.makeText(MovieListActivity.this, "您点击了电影"+movieName+"电影名为"+ temp.get("movie_chinese_name").toString(), Toast.LENGTH_SHORT).show();

                /*
                //此处可以得到电影名和影院名传出
                Intent intent = new Intent();
                intent.setClass(MovieListActivity.this, ChooseSessionActivity.class);

                intent.putExtra("CinemaName", movieName);
                intent.putExtra("MovieName", temp.get("movie_chinese_name").toString());
                MovieListActivity.this.startActivity(intent);
                */

            }
        });
        Toast.makeText(MovieListActivity.this, "11111", Toast.LENGTH_SHORT).show();
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
