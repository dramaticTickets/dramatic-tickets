package com.example.chen.dramatic_tickets.model;

/**
 * Created by chennan on 2018/6/29.
 */

public class MovieSession {
    private int sessionId;

    private int date;
    private int startTime;
    private int hallNum;
    private float price;


    public int getStartTime() {
        return startTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getDate() {
        return date;
    }

    public int getHallNum() {
        return hallNum;
    }

    public float getPrice() {
        return price;
    }

}
