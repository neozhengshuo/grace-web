package com.zhs.utils;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VolumeUtils {
    /**
     * 计算在指定的天数内有无大量出现
     * 大量：指定天数尽量的1.5倍及以上。
     * @param barSeries 日线数据
     * @param days 天数
     * @param multiple 放量的倍数
     * @return
     */
    static public boolean isExpandVolume(BarSeries barSeries, int days, float multiple){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        BarSeries subBarSeries = barSeries.getSubSeries(endIndex-(days-1),endIndex+1);
        int subEndIndex = subBarSeries.getEndIndex();
        List<Float> volList = new ArrayList<>();
        VolumeIndicator volumeIndicator = new VolumeIndicator(subBarSeries);
        SMAIndicator smaIndicator = new SMAIndicator(volumeIndicator,days);
        float average = smaIndicator.getValue(subEndIndex).floatValue();
        for (int j=subEndIndex;j>=0;j--){
            volList.add(subBarSeries.getBar(j).getVolume().floatValue());

        }
        float maxVol = Collections.max(volList);
        hit = maxVol/average>=multiple;

        return hit;
    }
}
