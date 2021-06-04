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

public class ShapeAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(ShapeAnalyzer.class);

    public ShapeAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    private BarInfo isLargeAndLongKLine(BaseBarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<6) return null;
        for (int i=endIndex;i>endIndex-5;i--){
            Bar bar = barSeries.getBar(i);

            float current_open = bar.getOpenPrice().floatValue();
            float current_close = bar.getClosePrice().floatValue();
            float current_high = bar.getHighPrice().floatValue();
            float current_low = bar.getLowPrice().floatValue();
            float before_close = barSeries.getBar(i-1).getClosePrice().floatValue();
            float temp = (current_close-current_open)/current_close;
            if(temp>0.03){
                VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
                SMAIndicator vol_5_Indicator = new SMAIndicator(volumeIndicator,5);
                SMAIndicator vol_63_Indicator = new SMAIndicator(volumeIndicator,63);
                int current_vol = bar.getVolume().intValue();
                int vol_5 = vol_5_Indicator.getValue(i).intValue();
                int vol_63 = vol_63_Indicator.getValue(i).intValue();
                if(current_vol>vol_5 && current_vol>vol_63){
                    return new BarInfo(i,barSeries.getName(),bar.getSimpleDateName());
                }
            }
        }
        return null;
    }

    private boolean isPriceWithinRange(BaseBarSeries barSeries,BarInfo barInfo){
        int endIndex = barSeries.getEndIndex();
        Bar targetBar = barSeries.getBar(barInfo.getIndex());
        float entityHalfPrice = this.getEntityHalfPrice(targetBar);
        for (int i = barInfo.getIndex()+1;i<=endIndex;i++){
            boolean priceHit = barSeries.getBar(i).getClosePrice().floatValue()>entityHalfPrice;
            if (!priceHit){
                return false;
            }else{
                int current_vol = barSeries.getBar(i).getVolume().intValue();
                int target_vol = targetBar.getVolume().intValue();
                boolean volHit =current_vol<target_vol;
                if(!volHit){
                    return false;
                }
            }
        }
        return true;
    }

    private float getEntityHalfPrice(Bar bar){
        return (bar.getClosePrice().floatValue()-bar.getOpenPrice().floatValue())/2+bar.getOpenPrice().floatValue();
    }

    public List<String> analyzer(){
        List<String> result = new ArrayList<>();

        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
//            logger.info(String.format("Loaded %s",file));
            BarInfo hitBarInfo = this.isLargeAndLongKLine(barSeries);
            if(hitBarInfo!=null){
                if(barSeries.getEndIndex()-hitBarInfo.getIndex() == 4){
                    boolean hit = this.isPriceWithinRange(barSeries,hitBarInfo);
                    if (hit){
                        result.add(file);
                        logger.info(hitBarInfo.toString());
                    }
                }
            }
        }
        return result;
    }
}
