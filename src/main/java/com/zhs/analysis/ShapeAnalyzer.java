package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.BarInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 量缩价稳形态分析:
 * 1. 找到长红K棒（价涨3%，量大于5日和63日均线）， 找到后根据指定的参数确定在长红K后续的交易日中价格相对于长红K的波动范围，找到符合波动范围的股票
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
     * 量缩价稳形态分析。确定标的后，在30分钟上按平台交易操作。
     * @param fileList 待分析的品种。
     * @param days 形态天数，建议最小值为4
     * @param abovePricePercentage 长红K后股价上涨的幅度,相对于实体的上沿，其值为1,，一般设置在1.0以上。
     * @param underPricePercentage 长红K后股价上涨的幅度，相对于实体的上沿，其值为1,一般设置在1.0以下。
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
            // 计算买点,策略1
            //
//            float open = barSeries.getBar(barInfo.getIndex()).getOpenPrice().floatValue();
//            float close = barSeries.getBar(barInfo.getIndex()).getClosePrice().floatValue();
//            float solid = close-open;
//            float buyPrice = solid/2+open;
//            for (int i=barInfo.getIndex()+1;i<=endIndex;i++){
//                int current_vol = barSeries.getBar(i).getVolume().intValue();
//                int target_vol = longKBar.getVolume().intValue();
//                boolean lowerVolHit = current_vol<(target_vol/2);
////                boolean priceHit = barSeries.getBar(i).getClosePrice().floatValue()<=barSeries.getBar(i).getOpenPrice().floatValue();
//                boolean priceHit = barSeries.getBar(i).getLowPrice().floatValue()<=buyPrice;
//                if(lowerVolHit && priceHit){
//                    barInfo.setSignal("【BUY "+barSeries.getBar(i).getSimpleDateName()+"】");
//                }
//            }

            // 计算买点，策略3：只适用与形状天数为4（构造函数参数days等于4）
            //
            if(days == 4){
                Bar barCur = barSeries.getBar(barInfo.getIndex());
                Bar bar1 = barSeries.getBar(barInfo.getIndex()+1);
                Bar bar2 = barSeries.getBar(barInfo.getIndex()+2);
                Bar bar3 = barSeries.getBar(barInfo.getIndex()+3);

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

                if(volumeHit1 && volumeHit2 && priceHit1 && priceHit2 && priceHit3){
                    barInfo.setSignal("【BUY "+barSeries.getBar(endIndex).getSimpleDateName()+"】");
                }

                return barInfo;
            }

            // 计算买点,策略2
            //
            int upVolume = 0;
            int downVolume = 0;
            for (int i = barInfo.getIndex()+1;i<=endIndex;i++){
                float open  = barSeries.getBar(i).getOpenPrice().floatValue();
                float close  = barSeries.getBar(i).getClosePrice().floatValue();
                if(open<close){
                    upVolume++;
                }else{
                    downVolume++;
                }
            }
            if(upVolume>downVolume){
                barInfo.setSignal("【BUY "+barSeries.getBar(endIndex).getSimpleDateName()+"】");
            }

            return barInfo;
        }
    }

    private boolean calculateBuyPoint(BarSeries barSeries,Bar longKBar, int longKBarIndex){

        int endIndex = barSeries.getEndIndex();
        float open = longKBar.getOpenPrice().floatValue();
        float close = longKBar.getClosePrice().floatValue();
        float solid = close-open;
        float buyPrice = solid/2+open;
        for (int i=longKBarIndex+1;i<=endIndex;i++){
            int current_vol = barSeries.getBar(i).getVolume().intValue();
            int target_vol = longKBar.getVolume().intValue();
            boolean lowerVolHit = current_vol<(target_vol/2);
//                boolean priceHit = barSeries.getBar(i).getClosePrice().floatValue()<=barSeries.getBar(i).getOpenPrice().floatValue();
            boolean priceHit = barSeries.getBar(i).getLowPrice().floatValue()<=buyPrice;
            return lowerVolHit && priceHit;
        }
        return false;
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
