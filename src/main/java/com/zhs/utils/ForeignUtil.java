package com.zhs.utils;

import com.zhs.indicator.KdjIndicator;
import com.zhs.indicator.RsvIndicator;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

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

    static public boolean isContinuousIncrementUp(BarSeries barSeries , int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

//        String name = barSeries.getName();
//        String a = name;

        boolean hit = false;
        for(int i = endIndex;i>endIndex-days;i--){
            float p1 = barSeries.getBar(i).getClosePrice().floatValue();
            float p2 = barSeries.getBar(i-1).getClosePrice().floatValue();
            if(p2<=p1){
                hit = true;
            }else{
                hit = false;
                break;
            }
        }
        if(hit){
            float p1 = barSeries.getBar(endIndex).getClosePrice().floatValue();
            float p2 = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
            hit = p1>p2;
        }
        return hit;
    }


    static public boolean isTrendUp(BarSeries barSeries ,int ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<ma) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,ma);

        for(int i = endIndex;i>=1;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();

            if(currentSMA>beforeSMA){
                isUp = true;
            }else{
                break;
            }
        }
        return isUp;
    }

    static public boolean isCurrentIncrementUp(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        Bar currentBar = barSeries.getBar(endIndex);
        Bar before1Bar = barSeries.getBar(endIndex-1);

        float currentVolume = currentBar.getClosePrice().floatValue();
        float before1Volume = before1Bar.getClosePrice().floatValue();

        return currentVolume>before1Volume;
    }

    static public boolean isCurrentShrink(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        Bar currentBar = barSeries.getBar(endIndex);
        Bar before1Bar = barSeries.getBar(endIndex-1);

        float currentVolume = currentBar.getClosePrice().floatValue();
        float before1Volume = before1Bar.getClosePrice().floatValue();

        return currentVolume<before1Volume;
    }

    static public boolean isComeIn(BarSeries barSeries, int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        Bar currentBar = barSeries.getBar(endIndex);
        Bar before1Bar = barSeries.getBar(endIndex-1);

        float currentVolume = currentBar.getClosePrice().floatValue();
        float before1Volume = before1Bar.getClosePrice().floatValue();

        boolean hit1 = currentVolume>before1Volume;
        if(hit1){
            boolean hit2 = false;
            for(int i = endIndex-days;i<endIndex;i++){
                if(barSeries.getBar(i).getClosePrice().floatValue()==before1Volume){
                    hit2 = true;
                }else{
                    break;
                }
            }
            return hit2;
        }
        return false;
    }
}
