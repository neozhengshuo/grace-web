package com.zhs.utils;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

public class PriceUnit {

    /**
     * 价格和均线之间的距离 ，价格要大于均线。
     * @param barSeries
     * @param ma 均线
     * @param distance 距离
     * @return
     */
    public static boolean isPriceAndMaDistance(BarSeries barSeries,int ma,float distance){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);
        float smaValue = smaIndicator.getValue(endIndex).floatValue();
        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();

        return (currentClosePrice-smaValue)/smaValue<=distance;
    }

    /**
     * 价格突破指定的均线
     * @return
     */
    public static boolean isPriceBreakUp(BarSeries barSeries,int ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);
        float smaValue = smaIndicator.getValue(endIndex).floatValue();

        return currentClosePrice>currentOpenPrice && currentClosePrice>smaValue && currentOpenPrice<smaValue;
    }

    /**
     * 价格在特定天数内突破指定均线的。
     * @param barSeries
     * @param ma 均线
     * @param days  表示多少天前。如果为零则表示当天。
     * @return
     */
    public static boolean isPriceBreakUp(BarSeries barSeries,int ma,int days){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        String name = barSeries.getName();
        System.out.println(name);

        float currentClosePrice = barSeries.getBar(endIndex-days).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex-days).getOpenPrice().floatValue();

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);
        float smaValue = smaIndicator.getValue(endIndex-days).floatValue();

        return currentClosePrice>currentOpenPrice && currentClosePrice>smaValue && currentOpenPrice<smaValue;
    }

    /**
     * 价格在特定天数前上涨。
     * @param barSeries
     * @param days 天数，必须大于0
     * @param increase 上涨的幅度
     * @param volMa1 需要突破的均量1
     * @param volMa2 需要突破的均量2
     * @return
     */
    public static boolean isPriceIncreased(BarSeries barSeries,int days,float increase,int volMa1,int volMa2){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        int targetIndex = endIndex-(days-1);

        float currentVolume = barSeries.getBar(targetIndex).getVolume().floatValue();
        float currentClosePrice = barSeries.getBar(targetIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(targetIndex).getOpenPrice().floatValue();

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaMa1Indicator = new SMAIndicator(volumeIndicator,volMa1);
        SMAIndicator smaMa2Indicator = new SMAIndicator(volumeIndicator,volMa2);
        float smaMa1Value = smaMa1Indicator.getValue(targetIndex).floatValue();
        float smaMa2Value = smaMa2Indicator.getValue(targetIndex).floatValue();

        boolean hit0 = currentVolume > smaMa1Value && currentVolume > smaMa2Value;
        boolean hit1 = currentClosePrice>currentOpenPrice;
        boolean hit2 = (currentClosePrice-currentOpenPrice)/currentOpenPrice>=increase;

        return hit0 && hit1 && hit2;
    }

    /**
     * 价格在特定天数前上涨,并出现特定形态。
     * 形态：长阳后的3个交易日中，前两个交易日收红且量缩，第3个交易日收阴量缩且量是这3个交易日中最低的。（量缩价稳）
     * @param barSeries
     * @param daysAgo
     * @param increase
     * @param volMa1
     * @param volMa2
     * @return
     */
    public static boolean isPriceIncreasedWithShape1(BarSeries barSeries,int daysAgo,float increase,int volMa1,int volMa2){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<daysAgo) return false;

        int targetIndex = endIndex-(daysAgo-1);
        float currentVolume = barSeries.getBar(targetIndex).getVolume().floatValue();
        float currentClosePrice = barSeries.getBar(targetIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(targetIndex).getOpenPrice().floatValue();

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaMa1Indicator = new SMAIndicator(volumeIndicator,volMa1);
        SMAIndicator smaMa2Indicator = new SMAIndicator(volumeIndicator,volMa2);
        float smaMa1Value = smaMa1Indicator.getValue(targetIndex).floatValue();
        float smaMa2Value = smaMa2Indicator.getValue(targetIndex).floatValue();

        boolean hit0 = currentVolume > smaMa1Value && currentVolume > smaMa2Value;
        boolean hit1 = currentClosePrice>currentOpenPrice;
        boolean hit2 = (currentClosePrice-currentOpenPrice)/currentOpenPrice>=increase;

        boolean hit = hit0 && hit1 && hit2;
        if(hit && daysAgo>3){
            Bar barCur = barSeries.getBar(targetIndex);
            Bar bar1 = barSeries.getBar(targetIndex+1);
            Bar bar2 = barSeries.getBar(targetIndex+2);
            Bar bar3 = barSeries.getBar(targetIndex+3);

            int volume = barCur.getVolume().intValue();
            int volume1 = bar1.getVolume().intValue();
            int volume2 = bar2.getVolume().intValue();
            int volume3 = bar3.getVolume().intValue();
            boolean volumeHit1 = volume1<volume && volume2<volume && volume3<volume;
            boolean volumeHit2 = volume3<volume1 && volume3<volume2;

            float open1 = bar1.getOpenPrice().floatValue();
            float close1 = bar1.getClosePrice().floatValue();
            boolean priceHit1 = close1>=open1;

            float open2 = bar2.getOpenPrice().floatValue();
            float close2 = bar2.getClosePrice().floatValue();
            boolean priceHit2 = close2>=open2;

            float open3 = bar3.getOpenPrice().floatValue();
            float close3 = bar3.getClosePrice().floatValue();
            boolean priceHit3 = close3<=open3;

            hit = volumeHit1 && volumeHit2 && priceHit1 && priceHit2 && priceHit3;
        }
        return hit;
    }

    /**
     * 价格在特定天数前上涨,并出现特定形态。
     * 形态：长阳后的1天价格窄幅运动（上涨），浮动率一般在2%之内且量小于长阳的量，且价格没有跌破长阳实体的一半。（量大拉升后没有卖盘涌出）
     * @param barSeries
     * @param daysAgo
     * @param increase
     * @param volMa1
     * @param volMa2
     * @return
     */
    public static boolean isPriceIncreasedWithShape2(BarSeries barSeries,int daysAgo,float increase,int volMa1,int volMa2){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<daysAgo) return false;

        int targetIndex = endIndex-(daysAgo-1);
        float currentVolume = barSeries.getBar(targetIndex).getVolume().floatValue();
        float currentClosePrice = barSeries.getBar(targetIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(targetIndex).getOpenPrice().floatValue();

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaMa1Indicator = new SMAIndicator(volumeIndicator,volMa1);
        SMAIndicator smaMa2Indicator = new SMAIndicator(volumeIndicator,volMa2);
        float smaMa1Value = smaMa1Indicator.getValue(targetIndex).floatValue();
        float smaMa2Value = smaMa2Indicator.getValue(targetIndex).floatValue();

        boolean hit0 = currentVolume > smaMa1Value && currentVolume > smaMa2Value;
        boolean hit1 = currentClosePrice>currentOpenPrice;
        boolean hit2 = (currentClosePrice-currentOpenPrice)/currentOpenPrice>=increase;

        boolean hit = hit0 && hit1 && hit2;
        if(hit && daysAgo>2){
            Bar longKBar = barSeries.getBar(targetIndex);
            Bar bar1 = barSeries.getBar(targetIndex+1);

            int volume = longKBar.getVolume().intValue();
            int volume1 = bar1.getVolume().intValue();
            boolean volumeHit = volume1<volume;

            float open1 = bar1.getOpenPrice().floatValue();
            float close1 = bar1.getClosePrice().floatValue();
            float floatingRate = Math.abs((close1-open1)/open1);
            boolean floatingHit = floatingRate<=0.04;
            boolean priceUpHit = close1>=open1;

            float readBodyHalf = (currentClosePrice-currentOpenPrice)/2+currentOpenPrice; // 实体价格的一半
            boolean priceHit = close1>readBodyHalf;

            hit = volumeHit && floatingHit && priceHit && priceUpHit;
        }
        return hit;
    }

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
     * 当前价格在指定均线的下方
     * @param barSeries
     * @param ma
     * @return
     */
    public static boolean isCurrentPriceUnderMa(BarSeries barSeries,int ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma);

        float currentClosePrice = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float currentOpenPrice = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        float currentLowPrice = barSeries.getBar(endIndex).getLowPrice().floatValue();
        float maValue = smaIndicator.getValue(endIndex).floatValue();

        return currentClosePrice<=maValue;
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
