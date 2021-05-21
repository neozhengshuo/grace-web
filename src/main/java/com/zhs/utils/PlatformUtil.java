package com.zhs.utils;

import org.ta4j.core.BarSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlatformUtil {
    /**
     * 通过指定的天数和距离判断平台
     * @param barSeries
     * @param platformDays
     * @param distance
     * @return
     */
    static public boolean isPlatform(BarSeries barSeries,int platformDays,float distance){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<platformDays) return false;

        BarSeries newBarSeries = barSeries.getSubSeries(endIndex-(platformDays-1),endIndex+1);

        List<Float> platform = new ArrayList<>();
        for (int i = 0;i<=(newBarSeries.getEndIndex());i++){
            platform.add(newBarSeries.getBar(i).getClosePrice().floatValue());
        }

        float highPrice = Collections.max(platform);
        float lowPrice = Collections.min(platform);
        float difference = highPrice-lowPrice;
        if(difference==0) return false;

        float divide = difference/lowPrice;

        return divide<=distance;
    }
}
