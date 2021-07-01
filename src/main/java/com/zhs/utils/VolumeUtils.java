package com.zhs.utils;

import com.zhs.entities.dict.AboveUnder;
import com.zhs.entities.dict.RedGreen;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
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
        int shortVol = shortSmaIndicator.getValue(endIndex).intValue();
        int longVol = longSmaIndicator.getValue(endIndex).intValue();

        return currentVol<shortVol && currentVol < longVol;

    }

    /**
     * 判断当前量大于指定的短周期和长周期均量。
     * @param barSeries
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    static public boolean isHighVolume(BarSeries barSeries,int shortMa,int longMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longMa);

        int currentVol = barSeries.getBar(endIndex).getVolume().intValue();
        int shortVol = shortSmaIndicator.getValue(endIndex).intValue();
        int longVol = longSmaIndicator.getValue(endIndex).intValue();

        return currentVol>shortVol && currentVol > longVol;

    }

    /**
     * 判断第一个均量是否在第二均量的知道位置处
     * @param barSeries
     * @param aboveUnder
     * @param ma1
     * @param ma2
     * @return
     */
    static public boolean isMaVolumePosition(BarSeries barSeries, AboveUnder aboveUnder,int ma1, int ma2){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator sma1Indicator = new SMAIndicator(volumeIndicator,ma1);
        SMAIndicator sma2Indicator = new SMAIndicator(volumeIndicator,ma2);

        int ma1Vol = sma1Indicator.getValue(endIndex).intValue();
        int ma2Vol = sma2Indicator.getValue(endIndex).intValue();

        if(aboveUnder == AboveUnder.UNDER){
            return ma1Vol <  ma2Vol;
        }
        if(aboveUnder == AboveUnder.ABOVE){
            return ma1Vol > ma2Vol;
        }

        return false;
    }

    /**
     * 判断当前量小于指定周期的均量。
     * @param barSeries
     * @param ma 周期
     * @return
     */
    static public boolean isLowVolume(BarSeries barSeries,int ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(volumeIndicator,ma);

        int currentVol = barSeries.getBar(endIndex).getVolume().intValue();
        int maVol = smaIndicator.getValue(endIndex).intValue();

        return currentVol<maVol;
    }



    /**
     * 判断当前量是否为特定天数内，小于指定的短周期和长周期均量。
     * @param barSeries
     * @param days 特定的天数
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    static public boolean isLowVolume(BarSeries barSeries,int days,int shortMa,int longMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex < days+1) return false;

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longMa);

        for (int i = endIndex;i>endIndex-days;i--){
            int currentVol = barSeries.getBar(i).getVolume().intValue();
            int shortVol = shortSmaIndicator.getValue(i).intValue();
            int longVol = longSmaIndicator.getValue(i).intValue();

            if (currentVol < shortVol && currentVol< longVol){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断第一个均量是否大于第二个均量
     * @param barSeries
     * @param shortMa
     * @param longMa
     * @return
     */
    static public boolean isVolumeAbove(BarSeries barSeries,int shortMa,int longMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longMa);

        float shortVol = shortSmaIndicator.getValue(endIndex).floatValue();
        float longVol = longSmaIndicator.getValue(endIndex).floatValue();

        return shortVol > longVol;
    }

    /**
     * 判断量在指定的天数内，有多少次大于5日和63日均量（价上涨）。
     * @param barSeries
     * @param inDays 指定的天数内
     * @param times 出现多少次大于5日和63日均量的情况。
     * @return
     */
    static public boolean isVolumeGreater(BarSeries barSeries,int inDays,int times){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=inDays) return false;

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,5);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,63);

        int count = 0;
        for (int i = endIndex;i>=endIndex-inDays;i--){
            float open = barSeries.getBar(i).getOpenPrice().floatValue();
            float close = barSeries.getBar(i).getClosePrice().floatValue();
            if(close>open){
                int shortMaVolume = shortSmaIndicator.getValue(i).intValue();
                int longMaVolume = longSmaIndicator.getValue(i).intValue();
                int currentVolume = barSeries.getBar(i).getVolume().intValue();
                if(currentVolume>shortMaVolume && currentVolume>longMaVolume){
                    count++;
                }
                if(count>=times){
                    return true;
                }
            }
        }
        return false;
    }
}
