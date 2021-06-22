package com.zhs.strategies.trend;

import com.zhs.analysis.*;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class VolumeReduction_StablePrice2 {

    private List<String> firstTest(){
        List<String> results;
        results = FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        return results;
    }

    @Test
    public void test1(){

        List<String> results = new ArrayList<>();
        results = FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(50,50,10);

        KLineAnalyzer kLineAnalyzer = new KLineAnalyzer(results);
        results = kLineAnalyzer.getKlinePattern();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test2(){

        List<String> results;
        results = firstTest();

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(80,80,0);

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
