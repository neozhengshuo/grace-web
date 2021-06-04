package com.zhs.entities;

public class BarInfo {
    private int index;
    private String barName;
    private String hitDate;

    public BarInfo() {
    }

    public BarInfo(int index, String barName, String hitDate) {
        this.index = index;
        this.barName = barName;
        this.hitDate = hitDate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getHitDate() {
        return hitDate;
    }

    public void setHitDate(String hitDate) {
        this.hitDate = hitDate;
    }

    @Override
    public String toString() {
        return "BarInfo{" +
                "index=" + index +
                ", barName='" + barName + '\'' +
                ", hitDate='" + hitDate + '\'' +
                '}';
    }
}
