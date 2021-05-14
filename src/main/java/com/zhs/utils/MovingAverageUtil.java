package com.zhs.utils;

import com.zhs.entities.dict.MovingAverage;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class MovingAverageUtil {
    /**
     * 判断某条均线是否向上
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isTrendUp(BarSeries barSeries, MovingAverage ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        EMAIndicator smaIndicator =new EMAIndicator(closePriceIndicator,ma.getMaValue());

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

    /**
     * 判断某条均线是否向下
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isTrendDown(BarSeries barSeries, MovingAverage ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        EMAIndicator smaIndicator =new EMAIndicator(closePriceIndicator,ma.getMaValue());

        for(int i = endIndex;i>=1;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();

            if(currentSMA<=beforeSMA){
                isUp = true;
            }else{
                break;
            }
        }
        return isUp;
    }


    /**
     * 对三条均线进行比较，某条均线是否在其他两条均线的中间。
     * @param barSeries 股票
     * @param upMa 上方的均线
     * @param midMa 中间的均线
     * @param downMa 下方的均线
     * @return
     */
    public boolean isTrendBetween(BarSeries barSeries, MovingAverage upMa,MovingAverage midMa,MovingAverage downMa){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        EMAIndicator upMa_smaIndicator =new EMAIndicator(closePriceIndicator,upMa.getMaValue());
        EMAIndicator midMa_smaIndicator =new EMAIndicator(closePriceIndicator,midMa.getMaValue());
        EMAIndicator downMa_smaIndicator =new EMAIndicator(closePriceIndicator,downMa.getMaValue());

        float upMa_price = upMa_smaIndicator.getValue(endIndex).floatValue();
        float midMa_price = midMa_smaIndicator.getValue(endIndex).floatValue();
        float down_price = downMa_smaIndicator.getValue(endIndex).floatValue();

        return midMa_price<upMa_price && midMa_price>down_price;
    }

    /**
     * 判断两条均线的距离是否小于指定的值。
     * @param barSeries
     * @param upMa
     * @param downMa
     * @param distance
     * @return
     */
    public boolean isMaDistance(BarSeries barSeries, MovingAverage upMa,MovingAverage downMa,float distance){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        EMAIndicator upMa_smaIndicator =new EMAIndicator(closePriceIndicator,upMa.getMaValue());
        EMAIndicator downMa_smaIndicator =new EMAIndicator(closePriceIndicator,downMa.getMaValue());

        float upMa_price = upMa_smaIndicator.getValue(endIndex).floatValue();
        float down_price = downMa_smaIndicator.getValue(endIndex).floatValue();

        float d = Math.abs(upMa_price-down_price)/((upMa_price+down_price)/2);

        return d<distance;
    }

    /**
     * 金叉
     * @param barSeries 股票
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    public boolean isMaGoldCross(BarSeries barSeries,MovingAverage shortMa,MovingAverage longMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator short_ema_indicator = new EMAIndicator(closePriceIndicator,shortMa.getMaValue());
        EMAIndicator long_ema_indicator = new EMAIndicator(closePriceIndicator,longMa.getMaValue());

        float current_short_ema_vol = short_ema_indicator.getValue(endIndex).floatValue();
        float before_short_ema_vol = short_ema_indicator.getValue(endIndex-1).floatValue();
        float current_long_ema_vol = long_ema_indicator.getValue(endIndex).floatValue();
        float before_long_ema_vol = long_ema_indicator.getValue(endIndex-1).floatValue();

        boolean hit1 = current_short_ema_vol>=current_long_ema_vol;
        boolean hit2 = before_short_ema_vol<=before_long_ema_vol;

        return hit1 && hit2;
    }
}
