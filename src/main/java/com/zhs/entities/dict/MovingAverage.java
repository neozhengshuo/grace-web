package com.zhs.entities.dict;

public enum MovingAverage {
    MA5(5),
    MA11(11),
    MA18(18),
    MA20(20),
    MA30(30),
    MA31(31),
    MA34(34),
    MA44(44),
    MA60(60),
    MA63(63),
    MA144(144),
    MA169(169),
    MA250(250);
    private int maValue = 0;

    MovingAverage(int i) {
        maValue = i;
    }

    public int getMaValue(){
        return maValue;
    }
}
