package com.zhs.utils;

import com.zhs.entities.dict.MovingAverage;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
     * 判断第一条均线是否在第二条均线的下面。
     * @param barSeries
     * @param firstMa
     * @param secondMa
     * @return
     */
    public boolean isMaPositionBelow(BarSeries barSeries,MovingAverage firstMa,MovingAverage secondMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator first_ema_indicator = new EMAIndicator(closePriceIndicator,firstMa.getMaValue());
        EMAIndicator second_ema_indicator = new EMAIndicator(closePriceIndicator,secondMa.getMaValue());

        float current_first_ema_vol = first_ema_indicator.getValue(endIndex).floatValue();
        float current_second_ema_vol = second_ema_indicator.getValue(endIndex).floatValue();

        return current_first_ema_vol<=current_second_ema_vol;
    }

    /**
     * 判断第一条均线是否在第二条均线的上面。
     * @param barSeries
     * @param firstMa
     * @param secondMa
     * @return
     */
    public boolean isMaPositionAbove(BarSeries barSeries,MovingAverage firstMa,MovingAverage secondMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator first_ema_indicator = new EMAIndicator(closePriceIndicator,firstMa.getMaValue());
        EMAIndicator second_ema_indicator = new EMAIndicator(closePriceIndicator,secondMa.getMaValue());

        float current_first_ema_vol = first_ema_indicator.getValue(endIndex).floatValue();
        float current_second_ema_vol = second_ema_indicator.getValue(endIndex).floatValue();

        return current_first_ema_vol>=current_second_ema_vol;
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

    /**
     * 判断[价]是否在指定的两条均线之间
     * @param barSeries
     * @param aboveMa
     * @param belowMa
     * @return
     */
    public boolean isPriceBetweenMa(BarSeries barSeries,MovingAverage aboveMa,MovingAverage belowMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator above_ema_indicator = new EMAIndicator(closePriceIndicator,aboveMa.getMaValue());
        EMAIndicator below_ema_indicator = new EMAIndicator(closePriceIndicator,belowMa.getMaValue());

        float current_above_ema_vol = above_ema_indicator.getValue(endIndex).floatValue();
        float current_below_ema_vol = below_ema_indicator.getValue(endIndex).floatValue();
        float current_price = barSeries.getBar(endIndex).getClosePrice().floatValue();

        return current_price<=current_above_ema_vol && current_price>=current_below_ema_vol;
    }

    /**
     * 判断股价是否在某条均线的上面
     * @param barSeries
     * @param aboveMa
     * @param belowMa
     * @return
     */
    public boolean isPriceAboveMa(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator ema_indicator = new EMAIndicator(closePriceIndicator,ma.getMaValue());

        float ema_vol = ema_indicator.getValue(endIndex).floatValue();
        float close_price = barSeries.getBar(endIndex).getClosePrice().floatValue();

        return close_price>=ema_vol;
    }

    /**
     * 判断股价是否在某条均线的下面
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isPriceUnderMa(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator ema_indicator = new EMAIndicator(closePriceIndicator,ma.getMaValue());

        float ema_vol = ema_indicator.getValue(endIndex).floatValue();
        float close_price = barSeries.getBar(endIndex).getClosePrice().floatValue();

        return close_price<=ema_vol;
    }

    /**
     * 均线相等
     * @param barSeries
     * @param firstMa
     * @param secondMa
     * @return
     */
    public boolean isMaEqual(BarSeries barSeries,MovingAverage firstMa,MovingAverage secondMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator ema1_indicator = new EMAIndicator(closePriceIndicator,firstMa.getMaValue());
        EMAIndicator ema2_indicator = new EMAIndicator(closePriceIndicator,secondMa.getMaValue());

        float ema1_vol = ema1_indicator.getValue(endIndex).floatValue();
        float ema2_vol = ema2_indicator.getValue(endIndex).floatValue();

        // 四舍五入
        //
        ema1_vol = new BigDecimal(ema1_vol).setScale(2,RoundingMode.HALF_UP).floatValue();
        ema2_vol = new BigDecimal(ema2_vol).setScale(2, RoundingMode.HALF_UP).floatValue();

        return  ema1_vol == ema2_vol;
    }
}
