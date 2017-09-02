package com.example.stephen.fyp_driving_lessons;

/**
 * Created by stephencaldwell on 02/09/2017.
 */

public class Learners {
    private int _id, driverNum, numLessons, phone;
    private String name, email, address;

    public Learners() {
    }

    public Learners(int driverNum, int numLessons, int phone, String name, String email, String address) {
        this.driverNum = driverNum;
        this.numLessons = numLessons;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(int driverNum) {
        this.driverNum = driverNum;
    }

    public int getNumLessons() {
        return numLessons;
    }

    public void setNumLessons(int numLessons) {
        this.numLessons = numLessons;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
