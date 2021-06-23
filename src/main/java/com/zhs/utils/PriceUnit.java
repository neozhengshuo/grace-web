package com.zhs.utils;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class PriceUnit {
    /**
     * 判断当日价格是否在指定天数之前的价格之上（扣抵判断）
     * @return
     */
    public static boolean isPositionAbove(BarSeries barSeries,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=days) return false;

        float currentPrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float daysBeforePrice = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
        return currentPrice>daysBeforePrice;
    }

    /**
     * 判断当日价格是否在指定天数之前的价格之下（扣抵判断）
     * @return
     */
    public static boolean isPositionUnder(BarSeries barSeries,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=days) return false;

        float currentPrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float daysBeforePrice = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
        return currentPrice<daysBeforePrice;
    }

    /**
     * 判断当天价格是否上涨。
     * @param barSeries
     * @return
     */
    public static boolean isCurrentPriceUp(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        return currentClosePrice>=currentOpenPrice;
    }

    /**
     * 当前价接触到指定均线，但收盘价在指定均线之上
     * @param barSeries
     * @return
     */
    public static boolean isCurrentPriceTouchMa(BarSeries barSeries,int ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        float currentLowPrice = barSeries.getBar(endIndex).getLowPrice().floatValue();
        float maValue = smaIndicator.getValue(endIndex).floatValue();

        return currentLowPrice<=maValue && Math.max(currentClosePrice,currentOpenPrice)>maValue;
    }

    /**
     * 当前价格在指定均线的上方
     * @param barSeries
     * @param ma
     * @return
     */
    public static boolean isCurrentPriceAboveMa(BarSeries barSeries,int ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        float currentLowPrice = barSeries.getBar(endIndex).getLowPrice().floatValue();
        float maValue = smaIndicator.getValue(endIndex).floatValue();

        return currentClosePrice>=maValue;
    }

    /**
     * 判断当天的K是否为可靠的拐点。
     * @param barSeries
     * @return
     */
    public static boolean isReliableTurningPoint(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        Bar curBar = barSeries.getBar(endIndex);
        Bar bef1Bar = barSeries.getBar(endIndex-1);

        // 判断当天是否上涨
        //
        boolean isCurUp = curBar.getOpenPrice().floatValue()<curBar.getClosePrice().floatValue();

        // 判断前一天的价格是否下跌
        //
        boolean isBef1Down = bef1Bar.getOpenPrice().floatValue()>bef1Bar.getClosePrice().floatValue();

        if(isCurUp && isBef1Down){
            float curOpen = curBar.getOpenPrice().floatValue();
            float curClose = curBar.getClosePrice().floatValue();
            float curLow = curBar.getLowPrice().floatValue();
            float curHigh = curBar.getHighPrice().floatValue();
            float bef1Open = bef1Bar.getOpenPrice().floatValue();
            float bef1Close = bef1Bar.getClosePrice().floatValue();
            float bef1Low = bef1Bar.getLowPrice().floatValue();
            float bef1High = bef1Bar.getHighPrice().floatValue();

            // 判断：当天的开盘价大于前一天的最低价；当天的收盘价大于前一天的开盘价。
            //
            boolean hit1 = curOpen>bef1Low;
            boolean hit2 = curClose>bef1Open;
            return hit1 && hit2;
        }
        return false;
    }

    /**
     * 判断当天的K是否为可靠的拐点(加入实体判断)。
     * @param barSeries
     * @return
     */
    public static boolean isReliableTurningPointWithRealBody(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        Bar curBar = barSeries.getBar(endIndex);
        Bar bef1Bar = barSeries.getBar(endIndex-1);

        // 判断当天是否上涨
        //
        boolean isCurUp = curBar.getOpenPrice().floatValue()<curBar.getClosePrice().floatValue();

        // 判断前一天的价格是否下跌
        //
        boolean isBef1Down = bef1Bar.getOpenPrice().floatValue()>bef1Bar.getClosePrice().floatValue();

        if(isCurUp && isBef1Down){
            float curOpen = curBar.getOpenPrice().floatValue();
            float curClose = curBar.getClosePrice().floatValue();
            float curLow = curBar.getLowPrice().floatValue();
            float curHigh = curBar.getHighPrice().floatValue();
            float bef1Open = bef1Bar.getOpenPrice().floatValue();
            float bef1Close = bef1Bar.getClosePrice().floatValue();
            float bef1Low = bef1Bar.getLowPrice().floatValue();
            float bef1High = bef1Bar.getHighPrice().floatValue();

            // 判断前一天的实体是第二天实体的两倍以上
            //
            float curReadBody = PriceUnit.calculateReadBody(curBar);
            float bef1ReadBody = PriceUnit.calculateReadBody(bef1Bar);
            if(bef1ReadBody/curReadBody>2F){
                // 判断：当天的开盘价大于前一天的最低价；当天的收盘价大于前一天的开盘价。
                //
//                boolean hit1 = curOpen>bef1Low;
//                boolean hit2 = curClose>bef1Open;
//                return hit1 && hit2;

                // 判断：当天的收盘价在前一天的开盘价和收盘价之间。
                //
                float max = Math.max(bef1Close,bef1Open);
                float min = Math.min(bef1Close,bef1Open);
                return min<curClose && curClose<max;
            }
        }
        return false;
    }

    /**
     * 计算实体K线实体
     * @param bar
     * @return
     */
    public static float calculateReadBody(Bar bar){
        float top = Math.max(bar.getClosePrice().floatValue(),bar.getOpenPrice().floatValue());
        float bottom = Math.min(bar.getClosePrice().floatValue(),bar.getOpenPrice().floatValue());

        return top-bottom;
    }
}
