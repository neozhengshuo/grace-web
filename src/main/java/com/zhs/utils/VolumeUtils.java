package com.zhs.utils;

import com.zhs.entities.dict.RedGreen;
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

    /**
     * 判断当前量是否为前一天的指定倍数
     * 大量：指定天数尽量的1.5倍及以上。
     * @param barSeries 日线数据
     * @param days 天数
     * @return
     */
    static public boolean isExpandVolume2(BarSeries barSeries, RedGreen redGreen, int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        for (int i = endIndex;i>endIndex-days;i--){
            VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
            SMAIndicator vol_5_indicator = new SMAIndicator(volumeIndicator,5);
            SMAIndicator vol_63_indicator = new SMAIndicator(volumeIndicator,63);

            int vol_5 = vol_5_indicator.getValue(i).intValue();
            int vol_63 = vol_63_indicator.getValue(i).intValue();
            int current_vol = barSeries.getBar(i).getVolume().intValue();

            float open = barSeries.getBar(i).getOpenPrice().floatValue();
            float close = barSeries.getBar(i).getClosePrice().floatValue();

            if(current_vol>vol_5 && current_vol>vol_63){
                if(redGreen == RedGreen.RED){
                    if(close>open){
                        return true;
                    }
                }
                if(redGreen == RedGreen.GREEN){
                    if(close<open){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前量小于指定的短周期和长周期均量。
     * @param barSeries
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    static public boolean isLowVolume(BarSeries barSeries,int shortMa,int longMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longMa);
        int currentVol = barSeries.getBar(endIndex).getVolume().intValue();

        return currentVol<shortMa && currentVol < longMa;

    }
}
