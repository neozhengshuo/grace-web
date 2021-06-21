package com.zhs.indicator;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.UpDown;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

import java.util.List;

public class RsvIndicatorTest {
    @Test
    public void test(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();
        BaseBarSeries barSeries = FileStockDailyData.load(results.get(1));
        System.out.println("Loaded file:"+barSeries.getName());

        int endIndex = barSeries.getEndIndex();
        System.out.println("End Index:"+endIndex);


        TrendUpIndicator trendUpIndicator = new TrendUpIndicator(barSeries,31,63,250);
        System.out.println(trendUpIndicator.getValue(endIndex));


//        RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
//        KdjIndicator kdj_kIndicator = new KdjIndicator(rsvIndicator);

        StochasticOscillatorKIndicator rsv = new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator k = new KIndicator(rsv);
        System.out.println(k.getValue(endIndex));


    }
}
