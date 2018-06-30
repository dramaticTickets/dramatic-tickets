package com.example.chen.dramatic_tickets.model;

/**
 * Created by chennan on 2018/6/29.
 */

public class Cinema {
    //cinema
    private String cinemaName;
    private String address;
    private float lowestPrice;

    //cinema_hall
    private int hallNum;
    private int seatRowNum;
    private int seatColNum;

    public String getCinemaName() {
        return cinemaName;
    }

    public String getAddress() {
        return address;
    }

    public int getHallNum() {
        return hallNum;
    }

    public int getSeatRowNum() {
        return seatRowNum;
    }

    public int getSeatColNum() {
        return seatColNum;
    }

    public float getLowestPrice() {
        return lowestPrice;
    }
}
