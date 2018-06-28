package com.example.chen.dramatic_tickets;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class ChooseSeatActivity extends AppCompatActivity {
    public SeatTable seatTableView;
    private String MovieName = null;
    private String CinemaName = null;
    private String tinghaoMessage = null;
    private String startTimeMessage = null;;
    private String LeaveTime = null;
    private String TheDate = null;
    private String price = null;

    private int row = -1;
    private int col = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_seat);

        TextView mView;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            MovieName = extras.getString("MovieName");
            mView = (TextView) findViewById(R.id.cinema_name);
            mView.setText(MovieName);

            CinemaName = extras.getString("CinemaName");
            mView = (TextView) findViewById(R.id.title);
            mView.setText(CinemaName);

            tinghaoMessage = extras.getString("tinghaoMessage");

            startTimeMessage = extras.getString("startTimeMessage");
            TheDate = extras.getString("Date");
            mView = (TextView) findViewById(R.id.cinema_address);
            mView.setText(TheDate+" " + startTimeMessage);


            price = extras.getString("price");
            LeaveTime = extras.getString("LeaveTime");

        }

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName(tinghaoMessage+"荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            //此处是是否卖出票
            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);

        Button btn = (Button) findViewById(R.id.buy_tickets_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row = seatTableView.getRowNumber();
                col = seatTableView.getColumnNumber();
                if(row == -1 || col == -1) {
                    Toast.makeText(ChooseSeatActivity.this, "您必须选择一个座位", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(ChooseSeatActivity.this, ConfirmOrder.class);

                    intent.putExtra("MovieName", MovieName);
                    intent.putExtra("CinemaName", CinemaName);
                    intent.putExtra("tinghaoMessage", tinghaoMessage);
                    intent.putExtra("startTimeMessage", startTimeMessage);
                    intent.putExtra("TheDate", TheDate);
                    intent.putExtra("rowCol", (row+1)+"排"+(col+1)+"座");
                    intent.putExtra("price", price);
                    intent.putExtra("LeaveTime", LeaveTime);

                    ChooseSeatActivity.this.startActivity(intent);
                }
            }
        });
    }

}
