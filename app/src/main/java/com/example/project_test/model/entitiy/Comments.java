package com.example.project_test.model.entitiy;

public class Comments {
    String username;
    String companyName;
    String comment;
    float rate;
    long time;

    public Comments() {
    }

    public Comments(String username, String companyName, String comment, float rate, long time) {
        this.username = username;
        this.companyName = companyName;
        this.comment = comment;
        this.rate = rate;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
