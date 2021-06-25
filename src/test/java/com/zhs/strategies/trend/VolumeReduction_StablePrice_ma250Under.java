package com.zhs.strategies.trend;

import com.zhs.analysis.KDAnalyzer;
import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class VolumeReduction_StablePrice_ma250Under {
    private final String resultsOutput = "C:\\Users\\neozheng\\Desktop\\VolumeReduction_StablePrice_ma31.txt";

    /**
     * 严格的趋势向上
     * @return
     */
    private List<String> getTrendUp(){
        List<String> results;
        results = FileStockDailyData.getStockFilesWithFullPath();
        TrendAnalyzer trendAnalyzer;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(250,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(250,31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);


        return results;
    }

    /**
     * 趋势向上：63日均线支撑；量在5日、63日均量之下；J在5以下时拐头向上；
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

    /**
     * 趋势向上，63MA均线支撑（出现拐点）；量小于5日和63日均量；J值在0以下；
     */
    @Test
    public void trendUpTest2() throws IOException {
        List<String> results = this.getTrendUp();

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(80,80,0);

        // 当天价格上涨
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUp();

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

    /**
     * 趋势向上，63MA均线支撑（没有出现拐点）；量小于5日和63日均量；J值在0以下；
     */
    @Test
    public void trendUpTest3() throws IOException {
        List<String> results = this.getTrendUp();

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(80,80,0);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);

        FileUtil.writeResultsToFile(resultsOutput,results);
    }

   // @Test
    public void patternTest() throws IOException {
        List<String> results = FileUtil.readResultFormTxtFile(resultsOutput);
        System.out.println(results.size());

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getReliableTurningPointWithRealBody();

        String strOut = Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }


   // @Test
    public void temp1(){
        List<String> results;
        results = FileStockDailyData.getStockFilesWithFullPath();

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(15,18,5);

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
