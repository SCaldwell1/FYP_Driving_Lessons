package com.example.stephen.fyp_driving_lessons;



public class Bookings {
    private int _id;
    private String learnerName, address, date, time;

    public Bookings(String booking){

    }

    public Bookings(String learnerName, String address, String date, String time) {
        this.learnerName = learnerName;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public Bookings() {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

