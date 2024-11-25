package com.example.orderfoodbtl.Model;

public class Product {
    int id;
    private String name;
    private String category;
    private double rating;
    private double price;
    private int imageResId;
    private int quantity;
    private double totalPrice;

    public Product(String category, int imageResId, String name, double rating, double price) {
        this.category = category;
        this.imageResId = imageResId;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.quantity=1;
    }
    public Product(int id, String name, int imageResId, String category, double rating, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.category = category;
        this.rating = rating;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(int id, String name, int imageResId, String category, double rating, double price) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.category = category;
        this.rating = rating;
        this.price = price;
        this.quantity=1;
    }

    public double getTotalPrice() {
        return price*quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }
}
