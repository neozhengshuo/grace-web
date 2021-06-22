package com.zhs.indicator;

import com.zhs.datasource.FileStockDailyData;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class TrendUpPriceDownIndicatorTest {
    @Test
    public void test(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();


        List<String> output = new ArrayList<>();
        for (String file:results){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            int endIndex = barSeries.getEndIndex();
            TrendUpPriceDownIndicator trendUpPriceDownIndicator = new TrendUpPriceDownIndicator(barSeries);
            for (int i = endIndex;i>=0;i--){
                boolean hit = trendUpPriceDownIndicator.getValue(i);
                if(hit){
                    output.add(file);
                    System.out.println(hit+" "+barSeries.getName()+" "+barSeries.getBar(i).getSimpleDateName());
                    break;
                }
            }
        }
        System.out.println("hit count:"+output.size());
    }
}
