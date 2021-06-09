package com.zhs.entities;

public class BarInfo {
    private int index;
    private String barName;
    private String hitDate;
    private String signal;

    public BarInfo() {
    }

    public BarInfo(int index, String barName, String hitDate) {
        this.index = index;
        this.barName = barName;
        this.hitDate = hitDate;
        this.signal = "";
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal += "\r\n"+signal;
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
        if(this.signal != null){
            return "BarInfo{" +
                    "index=" + index +
                    ", barName='" + barName + '\'' +
                    ", hitDate='" + hitDate + '\'' +
                    ", signal='" + signal + '\'' +
                    '}';
        }else{
            return "BarInfo{" +
                    "index=" + index +
                    ", barName='" + barName + '\'' +
                    ", hitDate='" + hitDate + '\'' +
                    '}';
        }

    }
}
