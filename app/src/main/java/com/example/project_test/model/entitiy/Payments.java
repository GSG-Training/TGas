package com.example.project_test.model.entitiy;

public class Payments {
   private String user;
    private  String company;
    private int price;
    private String request_id;
    private  long date;

    public Payments() {
    }

    public Payments(String user, String company, int price, String request_id,long date) {
        this.user = user;
        this.company = company;
        this.price = price;
        this.request_id = request_id;
        this.date=date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
