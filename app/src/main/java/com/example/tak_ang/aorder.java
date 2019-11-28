package com.example.tak_ang;

public class aorder {

    private int order_id;
    private String date_ordered;
    private int table_id;
    private int status;

    public aorder(int order_id, String date_ordered, int table_id, int status) {
        this.order_id = order_id;
        this.date_ordered = date_ordered;
        this.table_id = table_id;
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(String date_ordered) {
        this.date_ordered = date_ordered;
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
