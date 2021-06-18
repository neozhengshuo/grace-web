package com.zhs.entities;

import java.time.LocalDateTime;

public class Kdj {
    private String dateTime;
    private float k;
    private float d;
    private float j;
    private float rsv;
    private int index;

    public Kdj() {
    }

    public float getRsv() {
        return rsv;
    }

    public void setRsv(float rsv) {
        this.rsv = rsv;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public float getJ() {
        return j;
    }

    public void setJ(float j) {
        this.j = j;
    }

    public Kdj(String dateTime, float k, float d, float j) {
        this.dateTime = dateTime;
        this.k = k;
        this.d = d;
        this.j = j;
    }

    @Override
    public String toString() {
        return "Kdj{" +
                "dateTime='" + dateTime + '\'' +
                ", k=" + k +
                ", d=" + d +
                ", j=" + j +
                ", rsv=" + rsv +
                ", index=" + index +
                '}';
    }
}
