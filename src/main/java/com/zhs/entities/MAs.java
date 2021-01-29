package com.zhs.entities;

import java.util.List;

public class MAs {
    private String dataTime;
    private float ma5;
    private float ma10;
    private float ma18;
    private float ma31;
    private float ma63;
    private float ma250;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public float getMa5() {
        return ma5;
    }

    public void setMa5(float ma5) {
        this.ma5 = ma5;
    }

    public float getMa10() {
        return ma10;
    }

    public void setMa10(float ma10) {
        this.ma10 = ma10;
    }

    public float getMa18() {
        return ma18;
    }

    public void setMa18(float ma18) {
        this.ma18 = ma18;
    }

    public float getMa31() {
        return ma31;
    }

    public void setMa31(float ma31) {
        this.ma31 = ma31;
    }

    public float getMa63() {
        return ma63;
    }

    public void setMa63(float ma63) {
        this.ma63 = ma63;
    }

    public float getMa250() {
        return ma250;
    }

    public void setMa250(float ma250) {
        this.ma250 = ma250;
    }

    public MAs(String dataTime, float ma5, float ma10, float ma18, float ma31, float ma63, float ma250) {
        this.dataTime = dataTime;
        this.ma5 = ma5;
        this.ma10 = ma10;
        this.ma18 = ma18;
        this.ma31 = ma31;
        this.ma63 = ma63;
        this.ma250 = ma250;
    }

    public MAs(String dataTime) {
        this.dataTime = dataTime;
    }

    public MAs() {
    }

    @Override
    public String toString() {
        return String.format("DataTime:%s MA5:%s MA10:%s MA18:%s MA31:%s M63:%s M250:%s",
                dataTime,ma5,ma10,ma18,ma31,ma63,ma250);
    }
}
