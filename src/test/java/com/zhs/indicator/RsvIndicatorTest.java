package com.zhs.indicator;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Kdj;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

import java.util.ArrayList;
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

        StochasticOscillatorKIndicator stochasticOscillatorKIndicator = new StochasticOscillatorKIndicator(barSeries,9);
        KIndicator kIndicator = new KIndicator(stochasticOscillatorKIndicator);
        System.out.println(kIndicator.getValue(endIndex));

//
//        RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
//        KdjIndicator kdj_kIndicator = new KdjIndicator(rsvIndicator);
//
//        for (int i = 0;i<kdjList.stream().count();i++){
//            System.out.println(kdj_kIndicator.getKdj());
//        }

    }
}
