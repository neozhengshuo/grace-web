package com.zhs.utils;

import org.ta4j.core.BarSeries;

public class PriceUnit {
    /**
     * 判断当日价格是否在指定天数之前的价格之上（扣抵判断）
     * @return
     */
    public static boolean isPositionAbove(BarSeries barSeries,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=days) return false;

        float currentPrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float daysBeforePrice = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
        return currentPrice>daysBeforePrice;
    }

    /**
     * 判断当日价格是否在指定天数之前的价格之下（扣抵判断）
     * @return
     */
    public static boolean isPositionUnder(BarSeries barSeries,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=days) return false;

        float currentPrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float daysBeforePrice = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
        return currentPrice<daysBeforePrice;
    }

    /**
     * 判断当天价格是否上涨。
     * @param barSeries
     * @return
     */
    public static boolean isCurrentPriceUp(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        return currentClosePrice>=currentOpenPrice;
    }
}