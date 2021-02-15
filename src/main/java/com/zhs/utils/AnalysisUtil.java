package com.zhs.utils;

import com.zhs.entities.Kdj;
import com.zhs.entities.MAs;
import com.zhs.entities.Stock;
import com.zhs.indicator.DIndicator;
import com.zhs.indicator.JIndicator;
import com.zhs.indicator.KIndicator;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import java.util.*;

public class AnalysisUtil {
    /**
     * 计算指定BarSeries的KDJ指标。
     * @param barSeries
     * @return
     */
    public List<Kdj> calculationKdj(BarSeries barSeries){
        List<Kdj> kdjList = new ArrayList<>();

        StochasticOscillatorKIndicator stochasticOscillatorKIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(stochasticOscillatorKIndicator);
        DIndicator d = new DIndicator(k);
        JIndicator j = new JIndicator(k,d);

        for(int i = 0;i <= barSeries.getEndIndex();i++){
            Kdj kdj = new Kdj(barSeries.getBar(i).getDateName(),
                    k.getValue(i).floatValue(),
                    d.getValue(i).floatValue(),
                    j.getValue(i).floatValue());
            kdjList.add(kdj);
        }
        return kdjList;
    }

    /**
     * 判断KD是否在指定的值之下。
     * @param barSeries
     * @param kdValue
     * @return
     */
    public boolean lowerKdj(BarSeries barSeries,float kdValue){
        StochasticOscillatorKIndicator stochasticOscillatorKIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(stochasticOscillatorKIndicator);
        DIndicator d = new DIndicator(k);
        int endIndex = barSeries.getEndIndex();
        float kValue = k.getValue(endIndex).floatValue();
        float dValue = d.getValue(endIndex).floatValue();
        return (kValue<=kdValue && dValue<=kdValue);
    }

    /**
     * 计算所有指标，KDJ、MA，并返回Stock。
     * @param barSeries
     * @return
     */
    public Stock getStock(BarSeries barSeries){
        Stock stock = new Stock(barSeries.getName());
        stock.setKdj(this.calculationKdj(barSeries));
        stock.setMas(calculationMAs(barSeries));
        return stock;
    }

    /**
     * 计算MA（移动平均线）
     * @param barSeries
     * @return
     */
    public List<MAs> calculationMAs(BarSeries barSeries){
        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator5 = new SMAIndicator(closeIndicator,5);
        SMAIndicator smaIndicator10 = new SMAIndicator(closeIndicator,10);
        SMAIndicator smaIndicator18 = new SMAIndicator(closeIndicator,18);
        SMAIndicator smaIndicator31 = new SMAIndicator(closeIndicator,31);
        SMAIndicator smaIndicator63 = new SMAIndicator(closeIndicator,63);
        SMAIndicator smaIndicator250 = new SMAIndicator(closeIndicator,250);

        List<MAs> maList = new ArrayList<>();
        for(int i = 0;i<=barSeries.getEndIndex();i++){
            MAs mas = new MAs();
            mas.setDataTime(barSeries.getBar(i).getDateName());
            mas.setMa5(smaIndicator5.getValue(i).floatValue());
            mas.setMa10(smaIndicator10.getValue(i).floatValue());
            mas.setMa18(smaIndicator18.getValue(i).floatValue());
            mas.setMa31(smaIndicator31.getValue(i).floatValue());
            mas.setMa63(smaIndicator63.getValue(i).floatValue());
            mas.setMa250(smaIndicator250.getValue(i).floatValue());
            maList.add(mas);
        }

        return maList;
    }

    /**
     * 判断某均线是否在指定的天数内持续向上
     * @param barSeries 指定待分析的序列
     * @param ma 移动平均线
     * @param continued 持续天数
     * @return 是或否
     */
    public boolean isTrendUpwards(BarSeries barSeries, int ma, int continued){
        boolean isUp = true;

        int  barCount = barSeries.getBarCount();
        if(barCount < continued) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);

        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,ma);
        for(int i = barCount-1;i>barCount-continued;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();
            if(currentSMA<=beforeSMA){
                isUp = false;
                break;
            }
        }
        return isUp;
    }


    /**
     * 判断指定的Stock是否为趋势向上
     * @param stock 指定的Stock
     * @param continued 趋势向上持续的天数
     * @return
     */
    public boolean isTrendUpwardsWithMa31(Stock stock, int continued){
        boolean isUp = true;

        int dayCount = stock.getDayCount();
        if (dayCount < continued) return false;

        for(int i = dayCount-1;i>dayCount-continued;i--){
            float currentSMA = stock.getMas().get(i).getMa31();
            float beforeSMA = stock.getMas().get(i-1).getMa31();
            if(currentSMA<=beforeSMA){
                isUp = false;
                break;
            }
        }

        return isUp;
    }

    /**
     * 判断当前量是否在指定的均量上
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isUpWithVolumeMa(BarSeries barSeries,int ma){
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(volumeIndicator,ma);

        int currentIndex = barSeries.getEndIndex();
        if(currentIndex<=0) return false;

        float currentVol = barSeries.getBar(currentIndex).getVolume().floatValue();
        float maVol = smaIndicator.getValue(currentIndex).floatValue();

        return currentVol>=maVol;
    }

    /**
     * 判断量在指定的天数内成交量是否逐日缩量。
     * @param barSeries 序列
     * @param ma 指定天数的均量，一般取值5或63
     * @param continuedDays 连续低于均量的天数。
     * @return 是或否
     */
    public boolean isUpWithVolumeMa(BarSeries barSeries,int ma,int continuedDays){
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(volumeIndicator,ma);
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=continuedDays) return false;

        boolean hit = true;
        for(int i=endIndex-1;i>=endIndex-continuedDays;i--){
            float maVolume = smaIndicator.getValue(i).floatValue();
            float volume1 = barSeries.getBar(i).getVolume().floatValue();
            float volume2 = barSeries.getBar(i-1).getVolume().floatValue();
            if(volume1>volume2 || volume1>maVolume){
                hit = false;
                break;
            }
        }
        return hit;
    }

    /**
     * 判断股票是否在指定的天数内放量
     * @param barSeries 指定的股票日线序列
     * @param inDays 指定的天数
     * @return 是否符合条件
     */
    public boolean enlargeVolume(BarSeries barSeries,int inDays){
        boolean hit = true;

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator sma5Indicator = new SMAIndicator(volumeIndicator,5);
        SMAIndicator sma63Indicator = new SMAIndicator(volumeIndicator,63);

        int endIndex = barSeries.getEndIndex();
        if(endIndex > inDays){
            // 判断当天量是否为特定天数内最大的量。
            float current_volume = barSeries.getBar(endIndex).getVolume().floatValue();
            List<Integer> intArray = new ArrayList<>();
            for(int i = endIndex;i>=endIndex-inDays;i--){
                intArray.add(barSeries.getBar(i).getVolume().intValue());
            }
            int maxVolume = Collections.max(intArray);
            if(current_volume<maxVolume) {
                hit = false;
            }
        }
        return hit;

//        // 判断当天量
//        for(int i = endIndex;i>inDays-endIndex;i--){
//            float ma5_volume = volumeIndicator.getValue(i).floatValue();
//            float ma63_volume = volumeIndicator.getValue(i).floatValue();
//            if(current_volume<ma5_volume && current_volume<ma63_volume){
//                hit = false;
//                break;
//            }
//        }
    }

    /**
     * 判断指定的序列是否出现金叉向上
     * @param barSeries 指定的序列
     * @return 是或否
     */
    public boolean isKdGoldCross(BarSeries barSeries){
        // k快，d慢
        StochasticOscillatorKIndicator rsv = new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(rsv);
        DIndicator d = new DIndicator(k);

        int currentIndex = barSeries.getEndIndex();

        float currentK = k.getValue(currentIndex).floatValue();
        float currentD = d.getValue(currentIndex).floatValue();
        float previousK = k.getValue(currentIndex-1).floatValue();
        float previousD = d.getValue(currentIndex-1).floatValue();
        float previous2K = k.getValue(currentIndex-1-1).floatValue();
        float previous2D = d.getValue(currentIndex-1-1).floatValue();

        return (previousK<=previousD && currentK>=currentD) || (previous2K<=previous2D && previousK>=previousD);
    }

    /**
     * 判断J值是否在50以下上涨
     * @param barSeries
     * @return
     */
    public boolean isKdjUp(BarSeries barSeries){
        // k快，d慢，j最快
        StochasticOscillatorKIndicator rsv = new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(rsv);
        DIndicator d = new DIndicator(k);
        JIndicator j = new JIndicator(k,d);

        int currentIndex = barSeries.getEndIndex();
        float currentJ = j.getValue(currentIndex).floatValue();
        float previousJ = j.getValue(currentIndex-1).floatValue();

        return currentJ<=50 && currentJ>=previousJ;
    }

    /**
     * 判断J值在指定数值的下方
     * @param value
     * @return
     */
    public boolean isJUnder(BarSeries barSeries,int value){
        // k快，d慢，j最快
        StochasticOscillatorKIndicator rsv = new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(rsv);
        DIndicator d = new DIndicator(k);
        JIndicator j = new JIndicator(k,d);

        int endIndex = barSeries.getEndIndex();
        if(endIndex>0){
            float currentJ = j.getValue(endIndex).floatValue();
            return currentJ<=value;
        }
        return false;
    }

    /**
     * 判断最低价进入Boll低轨
     * @param barSeries
     * @return
     */
    public boolean isBollLower(BarSeries barSeries){
        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator41 = new SMAIndicator(closeIndicator,41);
        StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closeIndicator,41);

        BollingerBandsMiddleIndicator mb = new BollingerBandsMiddleIndicator(smaIndicator41);
        BollingerBandsUpperIndicator up = new BollingerBandsUpperIndicator(mb,stdIndicator,barSeries.numOf(2));
        BollingerBandsLowerIndicator low = new BollingerBandsLowerIndicator(mb,stdIndicator,barSeries.numOf(2));

        int endIndex = barSeries.getEndIndex();
        if(endIndex>0){
            float currentLowPrice =barSeries.getBar(endIndex).getLowPrice().floatValue();
            float currentBollLower = low.getValue(endIndex).floatValue();
            boolean hit1 = currentLowPrice<=currentBollLower;
            return hit1;
        }else{
            return false;
        }
    }

    /**
     * 判断最低价是否进入Boll中轨。
     * @param barSeries
     * @return
     */
    public boolean isBollMid(BarSeries barSeries){
        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator41 = new SMAIndicator(closeIndicator,41);
        StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closeIndicator,41);

        BollingerBandsMiddleIndicator mb = new BollingerBandsMiddleIndicator(smaIndicator41);
        BollingerBandsUpperIndicator up = new BollingerBandsUpperIndicator(mb,stdIndicator,barSeries.numOf(2));
        BollingerBandsLowerIndicator low = new BollingerBandsLowerIndicator(mb,stdIndicator,barSeries.numOf(2));

        int endIndex = barSeries.getEndIndex();
        if(endIndex>0){
            float currentLowPrice =barSeries.getBar(endIndex).getLowPrice().floatValue();
            float currentBollMid = mb.getValue(endIndex).floatValue();
            return currentLowPrice<=currentBollMid;
        }
        return false;
    }
}
