package com.example.cnpm;

public class HoaDon_class {
    private  String id,date,time;
    private  int total;

    public HoaDon_class(String id, String date, String time, int total) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.total = total;
    }

    public HoaDon_class() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
