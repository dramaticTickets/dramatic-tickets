package com.example.chen.dramatic_tickets.model;

public class Ticket {
    private int ticketId;
    private String userName;
    private String movieName;
    private int startTime;
    private int date;
    private String cinemaName;
    private int hallNum;
    private int seatRow;
    private int seatCol;
    private boolean isPrinted;
    private float price;

    public int getTicketId() {
        return ticketId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDate() {
        return date;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public int getHallNum() {
        return hallNum;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public boolean getIsPrinted() {
        return isPrinted;
    }

    public float getPrice() {
        return price;
    }
}
