package com.zhs.strategies.trend;

import com.zhs.analysis.KDAnalyzer;
import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 趋势向上，量缩价稳
 */
public class VolumeReduction_StablePrice {
//    private List<String> results = new ArrayList<>();
    private final String resultsOutput = "C:\\Users\\neozheng\\Desktop\\VolumeReduction_StablePrice.txt";

    /*
    * 趋势向上
    * */
//    @BeforeEach
//    public void trendDetermineTest(){
//        this.results = FileStockDailyData.getStockFilesWithFullPath();
//        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(this.results);
//        results = trendAnalyzer.getMaTrendUp(63);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(250);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(63,250);
//    }

    /**
     * 趋势向上
     * @return
     */
    private List<String> getTrendUp(){
        List<String> results;
        results = FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        return results;
    }


    /**
     * 趋势向上，18MA支撑
     */
    @Test
    public void test1() throws IOException {
        List<String> results = this.getTrendUp();

        // 趋势判定
        //
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        // 价格扣抵：价格在18、31、63日上。5日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(18);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(5);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // J值在低档向上
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(10);

        // 当天价格上涨
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    /**
     * 趋势向上，31MA支撑。
     */
    @Test
    public void test2() throws IOException {
        List<String> results = this.getTrendUp();

        // 价格扣抵：价格在31日上、10日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(18);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // J值在低档向上
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

        // 当天价格上涨
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    /**
     * 价格扣抵：63MA支撑。
     */
    @Test
    public void test3() throws IOException {
        List<String> results = this.getTrendUp();

        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

        // 当天价格上涨
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    /**
     * 趋势向上，忽略支撑
     */
    @Test
    public void test4() throws IOException {
        List<String> results = this.getTrendUp();

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KDJ在指定的值之下。
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(50,50,0);

        kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

        // 当天价格上涨
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    /**
     * 趋势向上，63MA支撑。
     */
    @Test
    public void test5() throws IOException {
        List<String> results = this.getTrendUp();

        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
//        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(10);

        // 当天价格上涨
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);

    }

    @Test
    public void temp() throws IOException {
        List<String> results = FileUtil.readResultFormTxtFile(resultsOutput);
        System.out.println(results.size());

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getReliableTurningPoint();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
