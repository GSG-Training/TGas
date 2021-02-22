package com.example.project_test.model.utils;

import java.util.HashMap;
import java.util.Map;

public class Company {
    private String email;
    private String phoneNumber;
    private String  CompanyName;
    private String FullName;
    private String password;
    private  String Address;
    private String city;
    private int p_voted;
    private float rate;

    public Company() {
    }

    public Company(String email, String phoneNumber, String CompanyName, String fullName, String password, String Address, String city,int p_voted,float rate) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.CompanyName = CompanyName;
        FullName = fullName;
        this.password = password;
        this.Address = Address;
        this.city = city;
        this.p_voted=p_voted;
        this.rate=rate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getP_voted() {
        return p_voted;
    }

    public void setP_voted(int p_voted) {
        this.p_voted = p_voted;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("phoneNumber", phoneNumber);
        result.put("password", password);
        result.put("FullName", FullName);
        result.put("city", city);
        result.put("Address", Address);
        result.put("p_voted", p_voted);
        result.put("rate", rate);
        result.put("CompanyName", CompanyName);


        return result;
    }
}
