package com.example.tak_ang;

public class chefuget {

    private int menu_id;
    private String name;
    private String description;
    private int price;
    private String image_path;
    private String categoryname;
    private int status;


    public chefuget(int menu_id, String name, String description, int price, String image_path, String categoryname, int status) {
        this.menu_id = menu_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;
        this.categoryname = categoryname;
        this.status = status;
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

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
