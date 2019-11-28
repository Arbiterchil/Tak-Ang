package com.example.tak_ang;

import java.util.Date;

public class reserget {

    private int booking_id;
    private String check_in;
    private int user_id;
    private int table_id;
    private int status;

    public reserget(int booking_id, String check_in, int user_id, int table_id, int status) {
        this.booking_id = booking_id;
        this.check_in = check_in;
        this.user_id = user_id;
        this.table_id = table_id;
        this.status = status;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
