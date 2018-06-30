package com.example.chen.dramatic_tickets.PersonalHistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.chen.dramatic_tickets.R;
import com.example.chen.dramatic_tickets.factory.ServiceFactory;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.model.Ticket;
import com.example.chen.dramatic_tickets.service.DramaticService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 2018/6/29.
 */

public class PersonalTicketHistory extends AppCompatActivity {

    private ArrayList<Map<String,Object>> ticketData= new ArrayList();
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_list);


        final ListView ticketList = (ListView) findViewById(R.id.tickets_list);
        //initTicket();

        simpleAdapter = new SimpleAdapter(PersonalTicketHistory.this,ticketData,R.layout.tickets_item,
                new String[]{"movie_chinese_name","ticket_date","ticket_time", "ticket_cinema"},
                new int[]{R.id.movie_chinese_name,R.id.ticket_date,R.id.ticket_time, R.id.ticket_cinema});
        ticketList.setAdapter(simpleAdapter);

        get_all_tickets("13719341638");

        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> temp = ticketData.get(position);
                int i = (int)temp.get("ticketId");
                Intent intent = new Intent();
                intent.putExtra("movie_chinese_name", temp.get("movie_chinese_name").toString());
                intent.putExtra("ticket_date", temp.get("ticket_date").toString());
                intent.putExtra("ticket_time",temp.get("ticket_time").toString());
                intent.putExtra("ticket_cinema",temp.get("ticket_cinema").toString());
                intent.putExtra("price", temp.get("price").toString());
                intent.putExtra("hallNum", temp.get("hallNum").toString());
                intent.putExtra("seatRow", temp.get("seatRow").toString());
                intent.putExtra("seatCol", temp.get("seatCol").toString());
                intent.putExtra("ticketId", i);
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

    public void get_all_tickets(String phone) {
        DramaticService service = ServiceFactory.createService(DramaticService.class);
        service.getAllTicket(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ticket[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Ticket[] tickets) {
                        for (int i = 0; i < tickets.length; i++) {
                            String movieName = tickets[i].getMovieName();
                            String userName = tickets[i].getUserName();
                            String cinemaName = tickets[i].getCinemaName();
                            int ticketId = tickets[i].getTicketId();
                            int startTime = tickets[i].getStartTime();
                            int date = tickets[i].getDate();
                            int hallNum = tickets[i].getHallNum();
                            int seatRow = tickets[i].getSeatRow();
                            int seatCol = tickets[i].getSeatCol();
                            float price = tickets[i].getPrice();
                            String time = startTime/100+":"+startTime%100;
                            if (startTime%100 < 10) {
                                time = startTime/100+":0"+startTime%100;
                            }
                            Map<String,Object> temp = new HashMap<String,Object>();
                            temp.put("movie_chinese_name", movieName);
                            temp.put("ticket_date", date/10000+"-"+date/100%100+"-"+date%100);
                            temp.put("ticket_time",time);
                            temp.put("ticket_cinema",cinemaName);
                            temp.put("price", price);
                            temp.put("hallNum", hallNum);
                            temp.put("seatRow", seatRow);
                            temp.put("seatCol", seatCol);
                            temp.put("ticketId", ticketId);
                            ticketData.add(temp);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
    /*private void initTicket() {//初始化影院信息
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
    }*/
}
