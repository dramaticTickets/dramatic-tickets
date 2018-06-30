package com.example.chen.dramatic_tickets.model;

/**
 * Created by chennan on 2018/6/29.
 */

public class User {
    private String account;
    private String phone;
    private String password;
    private String userName;
    private String picUrl;

    private int code;
    private String error;

    public String getPicUrl() {
        return picUrl;
    }

    public String getAccount() {
        return account;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public int getCode () {
        return code;
    }

    public String getError() {
        return error;
    }
}
