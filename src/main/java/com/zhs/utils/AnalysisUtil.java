package com.zhs.utils;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.entities.Kdj;
import com.zhs.entities.MAs;
import com.zhs.entities.Stock;
import com.zhs.entities.StockFeatures;
import com.zhs.entities.dict.KStickPosition;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.indicator.DIndicator;
import com.zhs.indicator.JIndicator;
import com.zhs.indicator.KIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
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

    private static final Logger logger = LoggerFactory.getLogger(AnalysisUtil.class);

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

    public boolean is250MaTrendUpwards(BarSeries barSeries){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,250);

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

    public boolean is63MaTrendUpwards(BarSeries barSeries){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,63);

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

    public boolean is31MaTrendUpwards(BarSeries barSeries){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,31);

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

    public boolean isMaTrendUpwards(BarSeries barSeries,int ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,ma);

        for(int i = endIndex;i>=1;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();

            if(currentSMA>=beforeSMA){
                isUp = true;
            }else{
                break;
            }
        }
        return isUp;
    }

    public boolean isMaTrendSmooth(BarSeries barSeries,MovingAverage ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,ma.getMaValue());

        for(int i = endIndex;i>=1;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();

            if(currentSMA==beforeSMA){
                isUp = true;
            }else{
                break;
            }
        }
        return isUp;
    }

    public boolean isMaTrendDown(BarSeries barSeries, int ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,ma);

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
     * 计算下影线，收红
     * @param barSeries
     * @return
     */
    public boolean isLowerShadow(BarSeries barSeries){
        boolean hit = false;

        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        Bar bar = barSeries.getBar(endIndex);
        float hight = bar.getHighPrice().floatValue();
        float low = bar.getLowPrice().floatValue();
        float open = bar.getOpenPrice().floatValue();
        float close = bar.getClosePrice().floatValue();

        float solid = Math.abs(open-close); // 计算实体

        float downShadow; // 计算下影线
        if(close>=open){
            downShadow = Math.abs(open-low);
        }else {
            return false;
        }

        float upShadow;  // 计算上影线
        if(open>close){
            upShadow = Math.abs(hight-open);
        }else {
            upShadow = Math.abs(hight-close);
        }


        hit = downShadow/solid>=2 && upShadow/downShadow<0.3;
        return hit;
    }



    /**
     * 判断某均线是否在指定的天数内持续向上,且大于年线（MA250）
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
        SMAIndicator ma250Indicator = new SMAIndicator(closePriceIndicator, MovingAverage.MA250.getMaValue());
        for(int i = barCount-1;i>=barCount-continued;i--){
            float currentSMA = smaIndicator.getValue(i).floatValue();
            float beforeSMA = smaIndicator.getValue(i-1).floatValue();
            float current_ma250Value = ma250Indicator.getValue(i).floatValue();
            float before_ma250Value = ma250Indicator.getValue(i-1).floatValue();
            if(currentSMA<beforeSMA || current_ma250Value<before_ma250Value || currentSMA<current_ma250Value){
                isUp = false;
                break;
            }
        }
        return isUp;
    }

    /**
     * 判断指定天数的EMA是否向上
     * @param barSeries 要计算的股票
     * @param ma EMA的周期
     * @param continued 指定的天数，在该天数内EMA是向上的
     * @return 是或否
     */
    public boolean is_ema_up(BarSeries barSeries,MovingAverage ma,int continued){
        boolean isUp = true;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<continued) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator emaIndicator = new EMAIndicator(closePriceIndicator,ma.getMaValue());
        for (int i = endIndex;i>=endIndex-continued;i--){
            float current_ema = emaIndicator.getValue(i).floatValue();
            float before_ema = emaIndicator.getValue(i-1).floatValue();
            if(current_ema<before_ema){
                isUp = false;
                break;
            }
        }

        return isUp;
    }

    /**
     * 价格向上突破指定的EMA
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean is_price_up_ema(BarSeries barSeries,MovingAverage ma){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator emaIndicator = new EMAIndicator(closePriceIndicator,ma.getMaValue());

        float close_price = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float open_price = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        float low_price = barSeries.getBar(endIndex).getLowPrice().floatValue();
        if(close_price>=open_price){
            float ema_price = emaIndicator.getValue(endIndex).floatValue();
            if(low_price<=ema_price){
                isUp = true;
            }
        }
        return isUp;
    }

    /**
     * 判断当前最低价离指定均线的距离
     * @param barSeries 股票
     * @param ma 均线
     * @param distance 距离
     * @return
     */
    public boolean is_price_distance_ema(BarSeries barSeries,MovingAverage ma,float distance){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator emaIndicator = new EMAIndicator(closePriceIndicator,ma.getMaValue());

        float low_price = barSeries.getBar(endIndex).getLowPrice().floatValue();
        float ema_price = emaIndicator.getValue(endIndex).floatValue();
        if(low_price>=ema_price){
            float d = (low_price-ema_price)/ema_price;
            if(d<distance){
                isUp = true;
            }
        }
        return isUp;
    }

    /**
     * EMA金叉
     * @param barSeries 要计算的股票
     * @param shortMa 短周期EMA
     * @param longMa 长周期EMA
     * @return 是或否
     */
    public boolean is_ema_golden_fork(BarSeries barSeries,MovingAverage shortMa,MovingAverage longMa){
        boolean isUp = false;
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
     * 指定两条均线，短周期均线在长周期均线下方。
     * @param barSeries
     * @param shortMa
     * @param longMa
     * @return
     */
    public boolean is_ema_down_ema(BarSeries barSeries,MovingAverage shortMa,MovingAverage longMa){
        boolean isUp = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=0) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator short_ema_indicator = new EMAIndicator(closePriceIndicator,shortMa.getMaValue());
        EMAIndicator long_ema_indicator = new EMAIndicator(closePriceIndicator,longMa.getMaValue());

        float current_short_ema_vol = short_ema_indicator.getValue(endIndex).floatValue();
        float current_long_ema_vol = long_ema_indicator.getValue(endIndex).floatValue();

        return current_short_ema_vol<=current_long_ema_vol;
    }

    /**
     * 趋势向上（MA63），且MA31在MA63之上，且MA31和MA63在MA250之上
     * @param barSeries
     * @param continued
     * @return
     */
    public boolean isTrendUpward_MA63(BarSeries barSeries,int continued){
        boolean hit = true;
        int barCount = barSeries.getBarCount();
        if(barCount<continued) return false;

        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma31_indicator = new SMAIndicator(closeIndicator,MovingAverage.MA31.getMaValue());
        SMAIndicator ma63_indicator = new SMAIndicator(closeIndicator,MovingAverage.MA63.getMaValue());
        SMAIndicator ma250_indicator = new SMAIndicator(closeIndicator,MovingAverage.MA250.getMaValue());
        for (int i = barCount-1;i>=barCount-continued;i--){
            float ma31_value = ma31_indicator.getValue(i).floatValue();
            float ma63_current_value = ma63_indicator.getValue(i).floatValue();
            float ma63_prov1_value = ma63_indicator.getValue(i-1).floatValue();
            float ma250_value = ma250_indicator.getValue(i).floatValue();
            if(ma63_current_value < ma63_prov1_value || ma31_value < ma63_current_value || ma63_current_value < ma250_value){
                hit = false;
                break;
            }
        }

        return hit;
    }

    /**
     * 判断最近3天任何1天的成交量大于63MA
     * @param barSeries barSeries
     * @param ma ma
     * @return 是否满足条件
     */
    public boolean isVolumeMoveThen(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<ma.getMaValue()) return false;

        boolean hit = false;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(volumeIndicator,ma.getMaValue());

        int current_volume = barSeries.getBar(endIndex).getVolume().intValue();
        int current_ma_volume = smaIndicator.getValue(endIndex).intValue();

        int prev1_volume = barSeries.getBar(endIndex-1).getVolume().intValue();
        int prev1_ma_volume = smaIndicator.getValue(endIndex-1).intValue();

        int prev2_volume = barSeries.getBar(endIndex-1).getVolume().intValue();
        int prev2_ma_volume = smaIndicator.getValue(endIndex-1).intValue();

        hit = current_volume>=current_ma_volume || prev1_volume>=prev1_ma_volume || prev2_volume>=prev2_ma_volume;

        return hit;
    }

    /**
     * 判断当前收盘价是否大于指定的均线
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isClosePriceMoveThen(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<ma.getMaValue()) return false;

        boolean hit = false;
        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());

        float current_close_price = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float ma_close_price = smaIndicator.getValue(endIndex).floatValue();
        hit = current_close_price>=ma_close_price;

        return hit;
    }

    /**
     * 判断是否出现KD金叉
     * @param barSeries
     * @return
     */
    public boolean isKdCrossUp(BarSeries barSeries){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<3) return hit;

        StochasticOscillatorKIndicator k = new StochasticOscillatorKIndicator(barSeries,9);
        StochasticOscillatorDIndicator d = new StochasticOscillatorDIndicator(k);

        float current_k = k.getValue(endIndex).floatValue();
        float current_d = d.getValue(endIndex).floatValue();
        float prev1_k = k.getValue(endIndex-1).floatValue();
        float prev1_d = d.getValue(endIndex-1).floatValue();
        float prev2_k = k.getValue(endIndex-1-1).floatValue();
        float prev2_d = d.getValue(endIndex-1-1).floatValue();

        boolean hit1 = current_k >= current_d && prev2_k <= prev2_d;
        boolean hit2 = current_k >= current_d && prev1_k <= prev1_d;
        boolean hit3 = prev1_k >= prev1_d && prev2_k <+ prev2_d;

        hit = hit1 || hit2 || hit3;
        return hit;
    }



    /**
     * 判断当前K值是否大于前一日的K值,且J值在K值的下面
     * @param barSeries
     * @return
     */
    public boolean is_k_or_j_up(BarSeries barSeries){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<3) return hit;

        StochasticOscillatorKIndicator stochasticOscillatorKIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(stochasticOscillatorKIndicator);
        DIndicator d = new DIndicator(k);
        JIndicator j = new JIndicator(k,d);

        float current_k = k.getValue(endIndex).floatValue();
        float prev1_k = k.getValue(endIndex-1).floatValue();
        float prev1_j = j.getValue(endIndex-1).floatValue();

        hit = (current_k>=prev1_k && prev1_j <= prev1_k);
        return hit;
    }

    /**
     * 均线纠结
     * @param barSeries
     * @param period 周期，在指定的交易日内进行判断
     * @param frequency 满足条件的次数（多周期均线在最高价和最低价之间）。
     * @return
     */
    public boolean is_moving_average_tangled(BarSeries barSeries,int period,int frequency){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<63 || endIndex<period || period<frequency) return false;

        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma1Indicator = new SMAIndicator(closeIndicator,MovingAverage.MA11.getMaValue());
        SMAIndicator ma2Indicator = new SMAIndicator(closeIndicator,MovingAverage.MA18.getMaValue());
        SMAIndicator ma3Indicator = new SMAIndicator(closeIndicator,MovingAverage.MA31.getMaValue());
        SMAIndicator ma4Indicator = new SMAIndicator(closeIndicator,MovingAverage.MA63.getMaValue());

        int count = 0;
        for (int i = endIndex;i>endIndex-period;i--){
            float ma1_val = ma1Indicator.getValue(i).floatValue();
            float ma2_val = ma2Indicator.getValue(i).floatValue();
            float ma3_val = ma3Indicator.getValue(i).floatValue();
            float ma4_val = ma4Indicator.getValue(i).floatValue();
            float highPrice = barSeries.getBar(i).getHighPrice().floatValue();
            float lowPrice = barSeries.getBar(i).getLowPrice().floatValue();

            boolean hit1 = highPrice>=ma1_val && highPrice >= ma2_val && highPrice >= ma3_val && highPrice >= ma4_val;
            boolean hit2 = lowPrice <= ma1_val && lowPrice <= ma2_val && lowPrice <= ma3_val && lowPrice <= ma4_val;
            if(hit1 && hit2){
                count++;
            }
        }
        if(count>=frequency){
            hit = true;
        }
        return hit;
    }

    /**
     * boll走平
     * @param barSeries
     * @param continued 持续天数
     * @param rate 波动率，取值在1以下比较合适。
     * @return
     */
    public boolean is_boll_parallel(BarSeries barSeries,int continued,float rate){
        boolean hit = true;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<continued) return false;

        ClosePriceIndicator closeIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator41 = new SMAIndicator(closeIndicator,31);
        StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closeIndicator,31);

        BollingerBandsMiddleIndicator mb = new BollingerBandsMiddleIndicator(smaIndicator41);
        BollingerBandsUpperIndicator up = new BollingerBandsUpperIndicator(mb,stdIndicator,barSeries.numOf(2));
        BollingerBandsLowerIndicator low = new BollingerBandsLowerIndicator(mb,stdIndicator,barSeries.numOf(2));

        float fixed_up_val = 0;
        float fixed_low_val = 0;
        for (int i = endIndex;i>endIndex-continued;i--){
            if(i == endIndex){
                fixed_up_val = up.getValue(i).floatValue();
                fixed_low_val = low.getValue(i).floatValue();
            }

            float up_val2 = up.getValue(i-1).floatValue();
            float up_r = Math.abs((up_val2-fixed_up_val)/fixed_up_val*100);
            float low_val2 = low.getValue(i-1).floatValue();
            float low_r = Math.abs((low_val2-fixed_low_val)/fixed_low_val*100);
            if(low_r>rate ){
                hit = false;
                break;
            }
        }

        return hit;
    }


    /**
     * 量缩价稳。计算逻辑：取一定天数内每天的最低价，计算每天最低价相对于前一天的浮动率，且每日量能小于5日均量，且红K大于绿K。
     * @param barSeries barSeries
     * @param continued 持续的天数
     * @param floatingRate 最低价的浮动率（一般取值在0.5之间）
     * @return 是否符合条件
     */
    public boolean is_volume_shrink_price_stable(BarSeries barSeries,int continued, float floatingRate){
        boolean hit = true;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<continued) return false;

        int redCount = 0;
        int greenCount = 0;

        VolumeIndicator volIndicator = new VolumeIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(volIndicator,5);
        float absVal = 0F;
        for (int i = endIndex-continued;i<=endIndex-1;i++) {
            if(i == endIndex-continued){
                absVal = barSeries.getBar(i).getLowPrice().floatValue();
            }

            float lowPrice = barSeries.getBar(i+1).getLowPrice().floatValue();
            float vol = barSeries.getBar(i+1).getVolume().floatValue();
            float vol_ma = smaIndicator.getValue(i+1).floatValue();
            float result = Math.abs((lowPrice-absVal)/lowPrice*100);
            if(result>floatingRate ){
                hit = false;
                break;
            }

            float closePrice = barSeries.getBar(i+1).getClosePrice().floatValue();
            float openPrice = barSeries.getBar(i+1).getOpenPrice().floatValue();
            if(closePrice>=openPrice){
                redCount++;
            }else{
                greenCount++;
            }
        }
        if(hit){
            if(redCount<greenCount){
                hit = false;
            }
        }
        return hit;
    }

    /**
     * 判断均线的平滑度。在一定天数内某均线浮动率是否符合指定的参数。<br/>
     * 均线约平滑，说明均线纠结的可能性越大。短周期均线最为敏感。
     * @param barSeries
     * @param ma
     * @param continued
     * @param floatingRate
     * @return
     */
    public boolean is_moving_average_smooth(BarSeries barSeries,MovingAverage ma,int continued, float floatingRate){
        boolean hit = true;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<continued) return false;

        int redCount = 0;
        int greenCount = 0;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma_indicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());
        float absMa = 0F;
        for (int i = endIndex-continued;i<=endIndex-1;i++) {
            if(i == endIndex-continued){
                absMa = ma_indicator.getValue(i).floatValue();
            }

            float maVal = ma_indicator.getValue(i+1).floatValue();
            float result = Math.abs((maVal-absMa)/maVal*100);
            if(result>floatingRate ){
                hit = false;
                break;
            }
        }
        return hit;
    }

    /**
     * 最低价是否来的指定均线的下方
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean is_price_under_ma(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma_indicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());

        Bar endBar = barSeries.getBar(endIndex);
        float open = endBar.getOpenPrice().floatValue();
        float close = endBar.getClosePrice().floatValue();
        float high = endBar.getHighPrice().floatValue();
        float low = endBar.getLowPrice().floatValue();
        float ma_val = ma_indicator.getValue(endIndex).floatValue();

        boolean hit1 = low<=ma_val;
        boolean hit2 = open>=ma_val || close >= ma_val || high >= ma_val;
        return hit1 && hit2;
    }

    public boolean is_price_up_ma(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma_indicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());


        Bar endBar = barSeries.getBar(endIndex);
        float open = endBar.getOpenPrice().floatValue();
        float close = endBar.getClosePrice().floatValue();
        float high = endBar.getHighPrice().floatValue();
        float low = endBar.getLowPrice().floatValue();
        float ma_val = ma_indicator.getValue(endIndex).floatValue();

        boolean hit1 = close>=ma_val;
        return hit1;
    }

    /**
     * 最低价接触到指定的均线(收盘价在均线之上)
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean is_lower_price_touch_ma(BarSeries barSeries,MovingAverage ma){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma_indicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());

        Bar endBar = barSeries.getBar(endIndex);
        float open = endBar.getOpenPrice().floatValue();
        float close = endBar.getClosePrice().floatValue();
        float high = endBar.getHighPrice().floatValue();
        float low = endBar.getLowPrice().floatValue();
        float ma_val = ma_indicator.getValue(endIndex).floatValue();

        boolean hit1 = low<=ma_val && close > ma_val && close>=open;
        return hit1;
    }

    public boolean is_price_between_ma(BarSeries barSeries,MovingAverage upperMa,MovingAverage lowerMa){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator upper_ma_indicator = new SMAIndicator(closePriceIndicator,upperMa.getMaValue());
        SMAIndicator lower_ma_indicator = new SMAIndicator(closePriceIndicator,lowerMa.getMaValue());

        Bar endBar = barSeries.getBar(endIndex);
        float open = endBar.getOpenPrice().floatValue();
        float close = endBar.getClosePrice().floatValue();
        float high = endBar.getHighPrice().floatValue();
        float low = endBar.getLowPrice().floatValue();
        float upper_ma_val = upper_ma_indicator.getValue(endIndex).floatValue();
        float lower_ma_val = lower_ma_indicator.getValue(endIndex).floatValue();

        boolean hit1 = close<=upper_ma_val;
        boolean hit2 = close>=lower_ma_val;
        return hit1 && hit2;
    }

    public boolean is_price_overgo(BarSeries barSeries,int days,MovingAverage ma1,MovingAverage ma2,MovingAverage ma3){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        List<Float> highList = new ArrayList<>();
        List<Float> lowList =new ArrayList<>();
        List<Float> ma1_List = new ArrayList<>();
        List<Float> ma2_List = new ArrayList<>();
        List<Float> ma3_List = new ArrayList<>();

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator ma1_indicator = new SMAIndicator(closePriceIndicator,ma1.getMaValue());
        SMAIndicator ma2_indicator = new SMAIndicator(closePriceIndicator,ma2.getMaValue());
        SMAIndicator ma3_indicator = new SMAIndicator(closePriceIndicator,ma3.getMaValue());


        for (int i = endIndex;i>endIndex-days;i--){
            highList.add(barSeries.getBar(i).getHighPrice().floatValue());
            lowList.add(barSeries.getBar(i).getLowPrice().floatValue());

            ma1_List.add(ma1_indicator.getValue(i).floatValue());
            ma2_List.add(ma2_indicator.getValue(i).floatValue());
            ma3_List.add(ma3_indicator.getValue(i).floatValue());
        }
        float highPrice = Collections.max(highList);
        float lowPrice = Collections.min(lowList);
        float ma1HighValue = Collections.max(ma1_List);
        float ma1LowValue = Collections.min(ma1_List);
        float ma2HighValue = Collections.max(ma2_List);
        float ma2LowValue = Collections.min(ma2_List);
        float ma3HighValue = Collections.max(ma3_List);
        float ma3LowValue = Collections.min(ma3_List);

        boolean hit1 = ma1HighValue<= highPrice && ma2HighValue<= highPrice && ma3HighValue<= highPrice;
        boolean hit2 = ma1LowValue>= lowPrice && ma2LowValue>= lowPrice && ma3LowValue>= lowPrice;

        return  hit1 && hit2;
    }

    /**
     * 量缩价跌（最好连续2-3日），且价在31MA中间收红K
     * @param barSeries
     * @return
     */
    public boolean is_vol_price_low(BarSeries barSeries,int continued,MovingAverage ma){
        boolean hit = true;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<continued+1) return false;

        for (int i = endIndex;i>endIndex-continued;i--){
            float price1 = barSeries.getBar(i).getClosePrice().floatValue();
            float price2 = barSeries.getBar(i-1).getClosePrice().floatValue();

            float vol1 = barSeries.getBar(i).getVolume().floatValue();
            float vol2 = barSeries.getBar(i-1).getVolume().floatValue();

            if(price1>price2 || vol1>vol2){
                hit = false;
                break;
            }
        }

        if(hit){
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
            SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());
            float current_price = barSeries.getBar(endIndex).getClosePrice().floatValue();
            float ma_value = smaIndicator.getValue(endIndex).floatValue();
            hit = current_price>=ma_value;
        }

        return hit;
    }

    /**
     * 判断开盘价或最低价是否小于31MA
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isOpenPriceLowerPriceLessThan(BarSeries barSeries,MovingAverage ma){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<3) return hit;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());
        float current_open_price = barSeries.getBar(endIndex).getOpenPrice().floatValue();
        float current_lower_price = barSeries.getBar(endIndex).getLowPrice().floatValue();

        hit = current_open_price<=smaIndicator.getValue(endIndex).floatValue() || current_lower_price <= smaIndicator.getValue(endIndex).floatValue();
        return hit;
    }

    /**
     * 最近三天最低价或收盘价在31MA之下，但当天收盘价在MA31上方
     * @param barSeries
     * @param ma
     * @return
     */
    public boolean isClosePriceLowerPriceLessThan(BarSeries barSeries,MovingAverage ma){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<3) return hit;

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator smaIndicator = new SMAIndicator(closePriceIndicator,ma.getMaValue());

        float current_close = barSeries.getBar(endIndex).getClosePrice().floatValue();
        float prev1_close = barSeries.getBar(endIndex-1).getClosePrice().floatValue();
        float prev2_close = barSeries.getBar(endIndex-1-1).getClosePrice().floatValue();
        float prev3_close = barSeries.getBar(endIndex-1-1-1).getClosePrice().floatValue();

        float current_lower = barSeries.getBar(endIndex).getLowPrice().floatValue();
        float prev1_lower = barSeries.getBar(endIndex-1).getLowPrice().floatValue();
        float prev2_lower = barSeries.getBar(endIndex-1-1).getLowPrice().floatValue();
        float prev3_lower = barSeries.getBar(endIndex-1-1-1).getLowPrice().floatValue();

        float current_ma = smaIndicator.getValue(endIndex).floatValue();
        float prev1_ma = smaIndicator.getValue(endIndex-1).floatValue();
        float prev2_ma = smaIndicator.getValue(endIndex-1-1).floatValue();
        float prev3_ma = smaIndicator.getValue(endIndex-1-1-1).floatValue();

        boolean hit1 = prev1_close <= prev1_ma || prev1_lower <= prev1_ma;
        boolean hit2 = prev2_close <= prev2_ma || prev2_lower <= prev2_ma;
        boolean hit3 = prev3_close <= prev3_ma || prev3_lower <= prev3_ma;

        boolean hit4 = current_close >= current_ma;

        hit = (hit1 || hit2 || hit3) && hit4;

        return hit;
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
    }

    /**
     * 判断指定的序列是否出现KD金叉向上
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
     * 判断指定的两条均线是否出现金叉（短周期均线应该从下往上穿过长周期均线）
     * @param barSeries
     * @return
     */
    public boolean is_ma_gold_cross(BarSeries barSeries, MovingAverage shortMa,MovingAverage longMa){
        boolean hit = false;

        ClosePriceIndicator close_indicator = new ClosePriceIndicator(barSeries);
        SMAIndicator short_ma_indicator = new SMAIndicator(close_indicator,shortMa.getMaValue());
        SMAIndicator long_ma_indicator = new SMAIndicator(close_indicator,longMa.getMaValue());

        return hit;
    }

    /**
     * 找到指定天数内最大量的那一天。
     * @param barSeries
     * @param withInDays
     * @return
     */
    public StockFeatures get_large_volume(BarSeries barSeries,int withInDays){
        StockFeatures stockFeatures = null;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<withInDays) return null;

        int start = endIndex-withInDays;


        Map<String,Float> largeVolumeMap = new HashMap<>();
        for (int i = start;i<=endIndex;i++){
            String key = barSeries.getBar(i).getDateName();
            float volume = barSeries.getBar(i).getVolume().floatValue();
            largeVolumeMap.put(key,volume);
        }
        float max = Collections.max(largeVolumeMap.values());
        for (String key:largeVolumeMap.keySet()){
            if(largeVolumeMap.get(key) == max){
                stockFeatures = new StockFeatures();
                stockFeatures.setId(0);
                stockFeatures.setName(barSeries.getName());
                stockFeatures.setFeatures("large_volume");
                stockFeatures.setDate(key);
            }
        }
        return stockFeatures;
    }

    /**
     * 判断J值是否在50以下上涨,同时前2天的J呈现下跌。
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
        float previous1J = j.getValue(currentIndex-1).floatValue();
        float previous2J = j.getValue(currentIndex-1-1).floatValue();

        return currentJ<=50 && currentJ>=previous1J && previous1J<=previous2J;
    }

    /**
     * 判断J值是否在指定的值以下
     * @param barSeries
     * @param jValue
     * @return
     */
    public boolean isJLower(BarSeries barSeries,float jValue){
        // k快，d慢，j最快
//        StochasticOscillatorDIndicator
//        StochasticOscillatorKIndicator stochasticOscillatorKIndicator =
//                new StochasticOscillatorKIndicator(barSeries,9);
//        KIndicator k = new KIndicator(stochasticOscillatorKIndicator);
//        DIndicator d = new DIndicator(k);
//        JIndicator j = new JIndicator(k,d);

        StochasticOscillatorKIndicator stochasticOscillatorKIndicator =
                new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(stochasticOscillatorKIndicator);
        DIndicator d = new DIndicator(k);
        JIndicator j = new JIndicator(k,d);

//        StochasticOscillatorKIndicator k =
//                new StochasticOscillatorKIndicator(barSeries,9);
//        StochasticOscillatorDIndicator d = new StochasticOscillatorDIndicator(k);
//        JIndicator j = new JIndicator(k,d);

        boolean hit = false;

        int endIndex = barSeries.getEndIndex();
        if(endIndex>9){
            float currentJ = j.getValue(endIndex).floatValue();
            hit = currentJ<=jValue;
        }
        return hit;
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

    /**
     * 判断是否为长K棒,如果是长红K，判断长红K以后交易日价有没有跌破长红K的一半。
     * @param barSeries
     * @param multiple 指定当前K棒的实体是前一天K棒的倍数
     * @param withInDays 指定多少天内价没有跌破K的哪个位置。
     * @param kStickPosition 指定价在K棒的位置，表示特定的天数内价没有跌破该位置。
     * @return
     */
    public boolean is_long_k_stick(BarSeries barSeries, float multiple,int withInDays, KStickPosition kStickPosition){

        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=1) return false;

        VolumeIndicator vol_Indicator = new VolumeIndicator(barSeries);
        SMAIndicator ma5_vol_indicator = new SMAIndicator(vol_Indicator,5);
        SMAIndicator ma63_vol_indicator = new SMAIndicator(vol_Indicator,63);
        int long_k_start_index = 0;
        for(int i = endIndex;i>0;i--){
            float current_open = barSeries.getBar(i).getOpenPrice().floatValue();
            float current_close = barSeries.getBar(i).getClosePrice().floatValue();
            float current_high = barSeries.getBar(i).getHighPrice().floatValue();
            float current_low = barSeries.getBar(i).getLowPrice().floatValue();
            float current_vol = barSeries.getBar(i).getVolume().floatValue();
            float current_ma5_vol = ma5_vol_indicator.getValue(i).floatValue();
            float current_ma63_vol = ma63_vol_indicator.getValue(i).floatValue();

            float prev1_open = barSeries.getBar(i-1).getOpenPrice().floatValue();
            float prev1_close = barSeries.getBar(i-1).getClosePrice().floatValue();
            float prev1_high = barSeries.getBar(i-1).getHighPrice().floatValue();
            float prev1_low = barSeries.getBar(i-1).getLowPrice().floatValue();

            if(current_close>current_open && prev1_close<current_close && current_high>prev1_high && current_vol>=current_ma5_vol){
                float current_spread = (current_close-current_open);
                float prev1_spread = Math.abs(prev1_close-prev1_open);
                float m_multiple = current_spread/prev1_spread;
                boolean hit1 = m_multiple>multiple;
                boolean hit2 = (endIndex-i)<withInDays;
                if(hit1 && hit2){
                    hit = true;
                    long_k_start_index = i;
                    break;
                }
            }
        }

        if(hit){
            float open = barSeries.getBar(long_k_start_index).getOpenPrice().floatValue();
            float close = barSeries.getBar(long_k_start_index).getClosePrice().floatValue();
            for (int j = long_k_start_index+1;j<=endIndex;j++){
                if(close>open){
                    if(kStickPosition == KStickPosition.HALF){
                        float spread = (close-open)/2;
                        float weaken = close-spread;
                        float next1 = barSeries.getBar(j).getClosePrice().floatValue();
                        if(next1<weaken){
                            hit = false;
                            break;
                        }
                    }
                    if(kStickPosition == KStickPosition.CLOSE){
                        float next1 = barSeries.getBar(j).getClosePrice().floatValue();
                        if(next1<close){
                            hit = false;
                            break;
                        }
                    }
                }
            }
        }
        if(hit){
            logger.info(String.format("%s-%s",barSeries.getName(),barSeries.getBar(long_k_start_index).getDateName()));
        }
        return hit;
    }

    /**
     * 严格的长红K棒
     * @param barSeries
     * @param multiple 指定长红K是前面K棒的倍数。
     * @return
     */
    public boolean is_strict_long_k_stick(BarSeries barSeries,float multiple){
        boolean hit = false;
        int endIndex = barSeries.getEndIndex();
        if(endIndex<=1) return false;

        VolumeIndicator vol_Indicator = new VolumeIndicator(barSeries);
        SMAIndicator ma5_vol_indicator = new SMAIndicator(vol_Indicator,5);
        SMAIndicator ma63_vol_indicator = new SMAIndicator(vol_Indicator,63);
        int long_k_start_index = 0;
        for(int i = endIndex;i>0;i--){
            float current_open = barSeries.getBar(i).getOpenPrice().floatValue();
            float current_close = barSeries.getBar(i).getClosePrice().floatValue();
            float current_high = barSeries.getBar(i).getHighPrice().floatValue();
            float current_low = barSeries.getBar(i).getLowPrice().floatValue();
            float current_vol = barSeries.getBar(i).getVolume().floatValue();
            float current_ma5_vol = ma5_vol_indicator.getValue(i).floatValue();
            float current_ma63_vol = ma63_vol_indicator.getValue(i).floatValue();

            float prev1_open = barSeries.getBar(i-1).getOpenPrice().floatValue();
            float prev1_close = barSeries.getBar(i-1).getClosePrice().floatValue();
            float prev1_high = barSeries.getBar(i-1).getHighPrice().floatValue();
            float prev1_low = barSeries.getBar(i-1).getLowPrice().floatValue();

            if(current_close>current_open && prev1_close<current_close && current_high>prev1_high){
                float current_spread = (current_close-current_open);
                float prev1_spread = Math.abs(prev1_close-prev1_open);
                float m_multiple = current_spread/prev1_spread;
                boolean hit1 = m_multiple>multiple;
                boolean hit2 = (endIndex-i)<8;
                if(hit1 && hit2){
                    hit = true;
                    long_k_start_index = i;
                    logger.info(String.format("%s-%s",barSeries.getName(),barSeries.getBar(long_k_start_index).getDateName()));
                    break;
                }
            }
        }
        return hit;
    }
}
