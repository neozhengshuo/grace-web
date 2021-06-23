package com.zhs.utils;

import com.zhs.entities.Kdj;
import com.zhs.indicator.KdjIndicator;
import com.zhs.indicator.RsvIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

public class KdjUtil {
    static public boolean isKLow(BarSeries barSeries ,float k){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
//        StochasticOscillatorKIndicator kIndicator =
//                new StochasticOscillatorKIndicator(barSeries,9);

        RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
        KdjIndicator kdjIndicator = new KdjIndicator(rsvIndicator);
        float kValue= kdjIndicator.getKdj(endIndex).getK();

        return kValue < k;
    }

    static public boolean isKLow(RsvIndicator indicator,float k){
        int endIndex = indicator.getBarSeries().getEndIndex();
        if(endIndex<1) return false;
        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        float kVal = kdjIndicator.getKdj(endIndex).getK();
        return kVal<k;
    }

    static public boolean isDLow(RsvIndicator indicator,float d){
        int endIndex = indicator.getBarSeries().getEndIndex();
        if(endIndex<1) return false;
        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        float dVal = kdjIndicator.getD(endIndex);
        return dVal<d;
    }

    static public boolean isKdjLow(RsvIndicator indicator,float k,float d,float j){
        int endIndex = indicator.getBarSeries().getEndIndex();
        if(endIndex<1) return false;
        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        Kdj kdj = kdjIndicator.getKdj(endIndex);
        return kdj.getK()<k && kdj.getD()<d && kdj.getJ()<j;
    }

    static public boolean isJLow(RsvIndicator indicator,float j){
        int endIndex = indicator.getBarSeries().getEndIndex();
        if(endIndex<1) return false;
        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        float jVal = kdjIndicator.getJ(endIndex);
        return jVal<j;
    }

    static public boolean isKLow(BarSeries barSeries ,int days,float k){
        int endIndex = barSeries.getEndIndex();
        if(endIndex < days+1) return false;


        StochasticOscillatorKIndicator kIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        for (int i = endIndex;i>endIndex-days;i--) {
            float kValue= kIndicator.getValue(i).floatValue();
            if(kValue<k){
                return true;
            }
        }
        return false;
    }


    /**
     * 判断J值是否在指定的值以下开始向上。
     * @param indicator
     * @param j
     * @return
     */
    static public boolean isJUp(RsvIndicator indicator,float j){
        boolean isUp = false;

        int endIndex = indicator.getBarSeries().getEndIndex();
        if(endIndex < 0) return false;

        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        for (int i = endIndex;i>=1;i--){
            float current_j = kdjIndicator.getJ(i);
            float before1_j = kdjIndicator.getJ(endIndex-1);

            if(current_j>before1_j && before1_j<j){
                isUp = true;
            }else {
                break;
            }
        }
        return isUp;
    }

    static public boolean isJUpWithVolume(RsvIndicator indicator,float j,int shortMa, int longMa){
        boolean isUp = false;

        BarSeries barSeries = indicator.getBarSeries();
        int endIndex = barSeries.getEndIndex();
        if(endIndex < 0) return false;

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longMa);

        KdjIndicator kdjIndicator = new KdjIndicator(indicator);
        for (int i = endIndex;i>=1;i--){
            float current_j = kdjIndicator.getJ(i);
            float before1_j = kdjIndicator.getJ(i-1);
            float before1_volume = barSeries.getBar(i-1).getVolume().floatValue();
            float before1_volume_shortMa = shortSmaIndicator.getValue(i-1).floatValue();
            float before1_volume_longMa = longSmaIndicator.getValue(i-1).floatValue();
            if(current_j>before1_j && before1_j<j && before1_volume< before1_volume_shortMa && before1_volume < before1_volume_longMa){
                isUp = true;
            }else {
                break;
            }
        }
        return isUp;
    }
}
