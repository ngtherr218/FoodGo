package com.example.orderfoodbtl.Model;

public class Invoice {
    int id;
    String userName;
    String date;
    String status;
    double total_amount;

    public Invoice(int id,String userName, String date, String status, double total_amount) {
        this.date = date;
        this.id = id;
        this.status = status;
        this.userName = userName;
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
