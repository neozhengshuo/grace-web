package com.zhs.utils;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

public class KDUtil {
    static public boolean isKLow(BarSeries barSeries ,float k){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        StochasticOscillatorKIndicator kIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        float kValue= kIndicator.getValue(endIndex).floatValue();

        return kValue < k;
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
}
