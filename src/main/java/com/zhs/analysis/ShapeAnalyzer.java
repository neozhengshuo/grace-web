package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.BarInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 量缩价稳形态分析:
 * 1. 找到长红K棒，达到指定的参数值（上涨幅度）且量大于5日均量以及63日均量。
 * 2. 根据形态的天数判断长红K棒后特定交易日（根据参数指定）内，股价没有跌破和突破指定的值。
 * 3. 设长红K棒实体部分为1，如要设置没有突破长红K实体的3%，其值就为1.03，若要设置价没有跌至长红K实体的一半，其值为0.5。
 */
public class ShapeAnalyzer {
    private final List<String> fileList;
    private final int days;
    private final float abovePricePercentage;
    private final float underPricePercentage;
    private static final Logger logger = LoggerFactory.getLogger(ShapeAnalyzer.class);


    /**
     * 量缩价稳形态分析。
     * @param fileList 待分析的品种。
     * @param days 形态天数
     * @param abovePricePercentage 长红K后股价上涨的幅度，一般设置在1.0以上。
     * @param underPricePercentage 长红K后股价上涨的幅度，一般设置在1.0以下。
     */
    public ShapeAnalyzer(List<String> fileList,int days,float abovePricePercentage, float underPricePercentage){
        this.fileList = fileList;
        this.days = days;
        this.abovePricePercentage = abovePricePercentage;
        this.underPricePercentage = underPricePercentage;
    }

    public ShapeAnalyzer(List<String> fileList){
        this.fileList = fileList;
        this.days = 5;
        this.abovePricePercentage = 1.3F;
        this.underPricePercentage = 0.5F;
    }

    /**
     * 判断是否在指定的天数里出现大量和长红K。
     * @param barSeries
     * @return
     */
    private BarInfo isLargeAndLongKLine(BaseBarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<this.days+1) return null;

        // 在指定天数内判断是否出现长红K
        for (int i=endIndex;i>endIndex-this.days;i--){
            Bar bar = barSeries.getBar(i);
            float current_open = bar.getOpenPrice().floatValue();
            float current_close = bar.getClosePrice().floatValue();
            float current_high = bar.getHighPrice().floatValue();
            float current_low = bar.getLowPrice().floatValue();
            float before_close = barSeries.getBar(i-1).getClosePrice().floatValue();
            float temp = (current_close-current_open)/current_close;

            // 判断上涨幅度有没有大于3%
            if(temp>0.03){
                VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
                SMAIndicator vol_5_Indicator = new SMAIndicator(volumeIndicator,5);
                SMAIndicator vol_63_Indicator = new SMAIndicator(volumeIndicator,63);
                int current_vol = bar.getVolume().intValue();
                int vol_5 = vol_5_Indicator.getValue(i).intValue();
                int vol_63 = vol_63_Indicator.getValue(i).intValue();

                // 判断当前的量是否大于5日均量和63日均量
                if(current_vol>vol_5 && current_vol>vol_63){
                    return new BarInfo(i,barSeries.getName(),bar.getSimpleDateName());
                }
            }
        }
        return null;
    }

    /**
     * 判断价格是否在一定的范围内波动,且缩量。并计算买点。
     *
     * @param barSeries
     * @param barInfo
     * @return
     */
    private BarInfo isPriceWithinRange(BaseBarSeries barSeries,BarInfo barInfo){
        int endIndex = barSeries.getEndIndex();
        Bar longKBar = barSeries.getBar(barInfo.getIndex());

        // 判断量和价在参数指定的范围内
        //
        float underPrice = this.getUnderPrice(longKBar,this.underPricePercentage);
        float abovePrice = this.getAbovePrice(longKBar,this.abovePricePercentage);
        boolean hit = false;
        for (int i = barInfo.getIndex()+1;i<=endIndex;i++){
            boolean abovePriceHit = barSeries.getBar(i).getClosePrice().floatValue()<abovePrice;
            boolean underPriceHit = barSeries.getBar(i).getClosePrice().floatValue()>underPrice;
            if (!underPriceHit || !abovePriceHit){
                hit = false;
                break;
            }else{
                int current_vol = barSeries.getBar(i).getVolume().intValue();
                int target_vol = longKBar.getVolume().intValue();
                boolean volHit =current_vol<target_vol;
                if(!volHit){
                    hit = false;
                    break;
                }else{
                    hit = true;
                }
            }
        }

        if(!hit){
            return null;
        }else{
            // 计算买点
            //
            for (int i=barInfo.getIndex()+1;i<=endIndex;i++){
                int current_vol = barSeries.getBar(i).getVolume().intValue();
                int target_vol = longKBar.getVolume().intValue();
                boolean lowerVolHit = current_vol<(target_vol/2);
                boolean priceHit = barSeries.getBar(i).getClosePrice().floatValue()<=barSeries.getBar(i).getOpenPrice().floatValue();
                if(lowerVolHit && priceHit){
                    barInfo.setSignal("【BUY "+barSeries.getBar(i).getSimpleDateName()+"】");
                }
            }
            return barInfo;
        }
    }

    /**
     * 计算长红K实体一半的价格。
     * @param bar
     * @return
     */
    private float getEntityHalfPrice(Bar bar, float abovePricePercentage, float underPricePercentage){
        return (bar.getClosePrice().floatValue()-bar.getOpenPrice().floatValue())/2+bar.getOpenPrice().floatValue();
    }

    private float getAbovePrice(Bar bar,float abovePricePercentage){
        float close = bar.getClosePrice().floatValue();
        float open = bar.getOpenPrice().floatValue();
        float solid = close-open;
        return solid*abovePricePercentage+open;
    }

    private float getUnderPrice(Bar bar,float underPricePercentage){
        float close = bar.getClosePrice().floatValue();
        float open = bar.getOpenPrice().floatValue();
        float solid = close-open;
        return solid*underPricePercentage+open;
//        return (bar.getClosePrice().floatValue()-bar.getOpenPrice().floatValue())*underPricePercentage+bar.getOpenPrice().floatValue();
    }

    /**
     *
     * @return
     */
    public List<String> analyzer(){
        List<String> result = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
//            logger.info(String.format("Loaded %s",file));
            BarInfo hitBarInfo = this.isLargeAndLongKLine(barSeries);
            if(hitBarInfo!=null){
                // 判断长红K棒是否出现在指定天数的第一天
                if(barSeries.getEndIndex()-hitBarInfo.getIndex() == this.days-1){
                    // 如果this.days-1 == 0表示this.days参数的值为1，那么找到长红K就行了。
                    if (this.days-1 == 0){
                        result.add(file);
                        logger.info(hitBarInfo.toString());
                    }else{
                        // 计算长红K当日以后的价格是否在指定的区间。
                        BarInfo barInfo = this.isPriceWithinRange(barSeries,hitBarInfo);
                        if (barInfo != null){
                            result.add(file);
                            logger.info(barInfo.toString());
                        }
                    }
                }
            }
        }
        return result;
    }
}
