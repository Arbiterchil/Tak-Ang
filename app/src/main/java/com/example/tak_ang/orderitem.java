package com.example.tak_ang;

public class orderitem {

    private int order_item_id;
    private int order_id;
    private int quantity;
    private String name;
    private double price;

    public orderitem(int order_item_id, int order_id, int quantity, String name, double price) {
        this.order_item_id = order_item_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}