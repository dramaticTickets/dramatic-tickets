package com.example.chen.dramatic_tickets.ChooseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.dramatic_tickets.ChooseSession.ChooseSessionActivity;
import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                new String[]{"movie_chinese_name","director_name","main_actor_name", "rate"},new int[]{R.id.movie_chinese_name,R.id.director_name,R.id.main_actor_name, R.id.star_rate}));

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = MovieData.get(position);

                if(temp.get("movie_chinese_name").toString().equals("复仇者联盟") && CinemaName.equals("金逸珠江国际影城广州大学城店")) {

                    final String MovieName = temp.get("movie_chinese_name").toString();
                    //此处可以得到电影名和影院名传出
                    DramaticService service = ServiceFactory.createService(DramaticService.class);
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
                                        Toast.makeText(ChooseMovie.this, "该电影尚未排期", Toast.LENGTH_SHORT).show();
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
                                    intent.setClass(ChooseMovie.this, ChooseSessionActivity.class);

                                    intent.putExtra("MovieName", MovieName);
                                    intent.putExtra("CinemaName", CinemaName);
                                    intent.putExtra("date", date);
                                    intent.putExtra("startTime", startTime);
                                    intent.putExtra("tinghao", tinghao);
                                    intent.putExtra("price", price);
                                    String phoneNumber = "13719341638";
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    ChooseMovie.this.startActivity(intent);
                                    //success = true;

                                }
                            });
                    //此处可以得到电影名和影院名传出
                } else {
                    Toast.makeText(ChooseMovie.this, "该电影暂未排期", Toast.LENGTH_SHORT).show();
                }

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
        MovieData.add(item);

        item2.put("movie_chinese_name", "复仇者联盟");
        item2.put("director_name", "乔斯·韦登");
        item2.put("main_actor_name","小罗伯特·唐尼, 克里斯·埃文斯");
        item2.put("rate","8.8");
        MovieData.add(item2);

        item3.put("movie_chinese_name", "死侍2");
        item3.put("director_name", "大卫·雷奇");
        item3.put("main_actor_name","瑞安·雷诺兹");
        item3.put("rate","7.9");
        MovieData.add(item3);

        item4.put("movie_chinese_name", "爵迹");
        item4.put("director_name", "郭敬明");
        item4.put("main_actor_name","范冰冰, 吴亦凡");
        item4.put("rate","3.8");
        MovieData.add(item4);
    }
}