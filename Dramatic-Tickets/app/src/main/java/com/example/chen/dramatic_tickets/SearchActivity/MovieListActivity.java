package com.example.chen.dramatic_tickets.SearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.ChooseActivity.ChooseCinema;
import com.example.chen.dramatic_tickets.ChooseActivity.ChooseMovie;
import com.example.chen.dramatic_tickets.R;

import java.util.ArrayList;
import java.util.HashMap;
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
        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            movieName = extras.getString("movieName");
            //检查数据库中相同部分unfinished
            initMovie();
        }


        movieList.setAdapter(new SimpleAdapter(MovieListActivity.this,MovieData,R.layout.search_moive_item,
                new String[]{"movie_chinese_name","director_name","main_actor_name","rate"},new int[]{R.id.movie_chinese_name,R.id.director_name,R.id.main_actor_name,R.id.star_rate}));

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = MovieData.get(position);
                if(movieName.equals("复仇者联盟")){
                    Intent intent = new Intent();
                    intent.setClass(MovieListActivity.this, ChooseCinema.class);

                    intent.putExtra("MovieName", temp.get("movie_chinese_name").toString());
                    MovieListActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(MovieListActivity.this, "该电影暂未排期", Toast.LENGTH_SHORT).show();
                }

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
    }

    private void initMovie() {//初始化影院信息
        Map<String,Object> item = new HashMap<String,Object>();
        Map<String,Object> item2 = new HashMap<String,Object>();
        Map<String,Object> item3 = new HashMap<String,Object>();
        Map<String,Object> item4 = new HashMap<String,Object>();
        item.put("movie_chinese_name", "头号玩家");
        item.put("director_name", "史蒂文·斯皮尔伯格");
        item.put("main_actor_name","泰伊·谢里丹,奥利维亚·库克");
        item.put("rate","7.5");
        if(movieName.equals("头号玩家")) {
            MovieData.add(item);
        }

        item2.put("movie_chinese_name", "复仇者联盟");
        item2.put("director_name", "乔斯·韦登");
        item2.put("main_actor_name","小罗伯特·唐尼, 克里斯·埃文斯");
        item2.put("rate","8.8");
        if(movieName.equals("复仇者联盟")) {
            MovieData.add(item2);
        }

        item3.put("movie_chinese_name", "死侍2");
        item3.put("director_name", "大卫·雷奇");
        item3.put("main_actor_name","瑞安·雷诺兹");
        item3.put("rate","7.9");
        if(movieName.equals("死侍")) {
            MovieData.add(item3);
        }

        item4.put("movie_chinese_name", "爵迹");
        item4.put("director_name", "郭敬明");
        item4.put("main_actor_name","范冰冰, 吴亦凡");
        item4.put("rate","3.8");
        if(movieName.equals("爵迹")) {
            MovieData.add(item4);
        }
    }
}
