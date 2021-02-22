package com.example.project_test.model.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Users implements Parcelable {
    private String email;
    private String phoneNumber;
    private String password;
    private String city;
    private String FullName;
    private int balance;

    public Users() {
    }

    public Users(String email, String phoneNumber, String password, String FullName, String city,int balance) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.FullName = FullName;
        this.city = city;
        this.balance=balance;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getCity() {
        return city;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(FullName);
        dest.writeString(city);
        dest.writeInt(balance);

    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Users(Parcel in) {
        email = in.readString();
        phoneNumber=in.readString();
        password=in.readString();
        FullName=in.readString();
        city=in.readString();
        balance=in.readInt();
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("phoneNumber", phoneNumber);
        result.put("password", password);
        result.put("FullName", FullName);
        result.put("city", city);
        result.put("balance",balance);
        return result;
    }
}
