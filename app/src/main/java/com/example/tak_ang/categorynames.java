package com.example.tak_ang;

public class categorynames {

    private int category_id;
    private String names;
    private int status;


    public categorynames(int category_id, String names, int status) {
        this.category_id = category_id;
        this.names = names;
        this.status = status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
