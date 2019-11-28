package com.example.tak_ang;

public class menuget {

    private int menu_id;
    private String name;
    private String description;
    private int price;
    private String image_path;
    private int categoty_id;

    public menuget(int menu_id, String name, String description, int price, String image_path, int categoty_id) {
        this.menu_id = menu_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;
        this.categoty_id = categoty_id;
    }


    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getCategoty_id() {
        return categoty_id;
    }

    public void setCategoty_id(int categoty_id) {
        this.categoty_id = categoty_id;
    }
}