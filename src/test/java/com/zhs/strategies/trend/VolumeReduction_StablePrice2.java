package com.zhs.strategies.trend;

import com.zhs.analysis.*;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.AboveUnder;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VolumeReduction_StablePrice2 {
    private final String resultsOutput = "C:\\Users\\neozheng\\Desktop\\VolumeReduction_StablePrice2.txt";

    /**
     * 严格的趋势向上
     * @return
     */
    private List<String> getTrendUp(){
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

    /**
     * 趋势向上；量在5日、63日均量之下；J在5以下时拐头向上；
     */
    @Test
    public void trendUpTest1() throws IOException {
        List<String> results = getTrendUp();

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(5);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    @Test
    public void patternTest() throws IOException {
        List<String> results = FileUtil.readResultFormTxtFile(resultsOutput);
        System.out.println(results.size());

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getReliableTurningPointWithRealBody();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp(){
        List<String> results;
        results = FileStockDailyData.getStockFilesWithFullPath();

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getReliableTurningPointWithRealBody();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

//    @Test
//    public void test2(){
//
//        List<String> results = new ArrayList<>();
//        results = FileStockDailyData.getStockFilesWithFullPath();
//
//        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(31);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(63);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(250);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(63,250);
//
//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
//        results = volumeAnalyzer.getLowVolume(5,63);
//
//        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
//        results = kdAnalyzer.getKdjLow(50,50,10);
//
//        KLineAnalyzer kLineAnalyzer = new KLineAnalyzer(results);
//        results = kLineAnalyzer.getKlinePattern();
//
//        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
//        FileUtil.writeTxtFile(strOut, results, true);
//    }

//    @Test
//    public void test3(){
//        List<String> results;
//        results = FileStockDailyData.getStockFilesWithFullPath();
//
//        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(31);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(63);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(31,63);
//
//       VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
//        results = volumeAnalyzer.getLowVolume(5,63);
//
//        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
//        results = kdAnalyzer.getKdjLow(80,80,0);
//
//        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
//        results = priceAnalyzer.getCurrentPriceUp();
//
//        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
//        FileUtil.writeTxtFile(strOut, results, true);
//    }
}
