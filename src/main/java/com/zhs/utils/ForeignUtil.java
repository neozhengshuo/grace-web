package com.zhs.utils;

import com.zhs.indicator.KdjIndicator;
import com.zhs.indicator.RsvIndicator;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

public class ForeignUtil {

    static public boolean isIncrementUp(BarSeries barSeries , float rate,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days+1) return false;

        Bar currentBar = barSeries.getBar(endIndex-days);
        Bar before1Bar = barSeries.getBar(endIndex-days-1);

        float currentVolume = currentBar.getClosePrice().floatValue();
        float before1Volume = before1Bar.getClosePrice().floatValue();

        if(currentVolume>before1Volume){
            return (currentVolume-before1Volume)/before1Volume>rate;

        }
        return false;
    }
}
