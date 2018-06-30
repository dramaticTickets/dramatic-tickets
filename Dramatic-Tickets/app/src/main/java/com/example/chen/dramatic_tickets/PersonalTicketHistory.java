package com.example.chen.dramatic_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/6/29.
 */

public class PersonalTicketHistory extends AppCompatActivity {

    private ArrayList<Map<String,Object>> ticketData= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_list);


        final ListView ticketList = (ListView) findViewById(R.id.tickets_list);
        initTicket();

        ticketList.setAdapter(new SimpleAdapter(PersonalTicketHistory.this,ticketData,R.layout.tickets_item,
                new String[]{"movie_chinese_name","ticket_date","ticket_time", "ticket_cinema"},
                new int[]{R.id.movie_chinese_name,R.id.ticket_date,R.id.ticket_time, R.id.ticket_cinema}));

        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = ticketData.get(position);
                Toast.makeText(PersonalTicketHistory.this, "您点击了电影票,电影名为"+ temp.get("movie_chinese_name").toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(PersonalTicketHistory.this, PersonalTicketDetail.class);
                PersonalTicketHistory.this.startActivity(intent);

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
        //Toast.makeText(PersonalTicketHistory.this, "11111", Toast.LENGTH_SHORT).show();

    }

    private void initTicket() {//初始化影院信息
        Map<String,Object> item = new HashMap<String,Object>();
        Map<String,Object> item2 = new HashMap<String,Object>();
        Map<String,Object> item3 = new HashMap<String,Object>();
        Map<String,Object> item4 = new HashMap<String,Object>();
        item.put("movie_chinese_name", "头号玩家");
        item.put("ticket_date", "2000-01-01");
        item.put("ticket_time","14:00");
        item.put("ticket_cinema","ABCD");
        ticketData.add(item);

        item2.put("movie_chinese_name", "复仇者");
        item2.put("ticket_date", "2000-01-01");
        item2.put("ticket_time","14:00");
        item2.put("ticket_cinema","ABCD");
        ticketData.add(item2);

        item3.put("movie_chinese_name", "死侍2");
        item3.put("ticket_date", "2000-01-01");
        item3.put("ticket_time","14:00");
        item3.put("ticket_cinema","ABCD");
        ticketData.add(item3);

        item4.put("movie_chinese_name", "爵迹");
        item4.put("ticket_date", "2000-01-01");
        item4.put("ticket_time","14:00");
        item4.put("ticket_cinema","ABCD");
        ticketData.add(item4);
    }
}
