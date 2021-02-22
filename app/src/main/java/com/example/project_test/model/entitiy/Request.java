package com.example.project_test.model.entitiy;

import java.util.HashMap;
import java.util.Map;

public class Request {
    String from;
    String to;
    String size;
    String address;
    String  phoneNumber;
    boolean isBuy;
    int price;
    int status; //determine the status of request (waiting accept,completed,canceled,,executing)
    long  date;


    public Request() {
    }

    public Request(String to, String from,String size , String address, String phoneNumber, boolean isBuy, int price,int status,long date) {
        this.from=from;
        this.to = to;
        this.size=size;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isBuy = isBuy;
        this.price = price;
        this.status=status;
        this.date=date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);
        result.put("size", size);
        result.put("address", address);
        result.put("phoneNumber", phoneNumber);
        result.put("isBuy",isBuy);
        result.put("price",price);
        result.put("status",status);
        result.put("date",date);

        return result;
    }
}
