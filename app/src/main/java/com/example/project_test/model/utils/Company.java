package com.example.project_test.model.utils;

public class Company {
    private String email;
    private String phoneNumber;
    private String  CompanyName;
    private String FullName;
    private String password;
    private  String Address;
    private String city;

    public Company() {
    }

    public Company(String email, String phoneNumber, String companyName, String fullName, String password, String address, String city) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        CompanyName = companyName;
        FullName = fullName;
        this.password = password;
        Address = address;
        this.city = city;
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

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
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

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
