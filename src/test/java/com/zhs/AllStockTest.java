package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.KStickPosition;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AllStockTest {

    /**
     * 强势个股筛选
     * 1. 多头排列（18MA、31MA、63MA），且均线值大于MA250。
     */
    @Test
    public void getTrendUp_MA18_MA31_MA63(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA18,MovingAverage.MA31,MovingAverage.MA63,5,20,40);
        System.out.println("多头排列(MA18、MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("多头排列(MA18、MA31、MA63)",results,true);
    }

    /**
     * 1. 多头排列（31MA、63MA），且均线值大于MA250。
     * 2. K值大于前一日K值，且J值在K值的下方
     * 3. 量缩价跌（最好连续2-3日），且价在31MA中间收红K  （未完成）
     */
    @Test
    public void getTrendUp_MA31_MA63(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,25,50);
        System.out.println("多头排列(MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("多头排列(MA31、MA63)：",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_k_or_j_up();
        System.out.println("K值向上"+results.size());
        FileUtil.writeTxtFile("K值向上",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getVolumeMoveThen(MovingAverage.MA63);
//        System.out.println("成交量大于MA63"+results.size());
//        FileUtil.writeTxtFile("成交量大于MA63",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getClosePriceLowerPriceLessThan(MovingAverage.MA31);
//        System.out.println("K值向上"+results.size());
//        FileUtil.writeTxtFile("K值向上",results,true);


//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getVolumeMoveThen(MovingAverage.MA63);
//        System.out.println("成交量大于MA63"+results.size());
//        FileUtil.writeTxtFile("成交量大于MA63",results,true);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getClosePriceMoveThen(MovingAverage.MA5);
//        System.out.println("收盘价大于MA5"+results.size());
//        FileUtil.writeTxtFile("收盘价大于MA5",results,true);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getKdCrossUp();
//        System.out.println("KD金叉"+results.size());
//        FileUtil.writeTxtFile("KD金叉",results,true);
    }

    @Test
    public void getTrendUp_MA21_MA63_(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,25,50);
        System.out.println("多头排列(MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("多头排列(MA31、MA63)：",results,true);

        MovingAverage ma = MovingAverage.MA31;
        int continued = 5;
        float floatingRate = 1.5F;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_moving_average_smooth(ma,continued,floatingRate);
        String str = String.format("量缩价稳(continued=%s,floatingRate=%s)",continued,floatingRate);
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * 弱势整理：（未开始）
     */
    @Test
    public void getTrendUp_MA63_Kup(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        int continued = 20;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp_MA63(continued);
        System.out.println("趋势向上："+results.size());
        FileUtil.writeTxtFile("趋势向上-MA63",results,true);


        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getVolumeMoveThen(MovingAverage.MA63);
        System.out.println("成交量大于MA63"+results.size());
        FileUtil.writeTxtFile("成交量大于MA63",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getClosePriceMoveThen(MovingAverage.MA5);
        System.out.println("收盘价大于MA5"+results.size());
        FileUtil.writeTxtFile("收盘价大于MA5",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_k_or_j_up();
        System.out.println("K值向上"+results.size());
        FileUtil.writeTxtFile("K值向上",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getOpenPriceLowerPriceLessThan(MovingAverage.MA31);
        System.out.println("收盘价或最低价小于31MA"+results.size());
        FileUtil.writeTxtFile("收盘价或最低价小于31MA",results,true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void test0(){

    }

    /**
     * 连续3日量缩价跌，当日价在31MA上方
     */
    @Test
    public void test1(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_vol_price_low(3,MovingAverage.MA63);
        System.out.println("get_vol_price_low"+results.size());
        FileUtil.writeTxtFile("get_vol_price_low",results,true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void test2(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        int period = 31;
        int frequency = 7;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_moving_average_tangled(period,frequency);

        String str = String.format("均线纠结(period=%s,frequency=%s)",period,frequency);
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * boll走平
     */
    @Test
    public void boll_parallel(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        // 参数组
        int continued = 9;
        float floatingRate = 0.135F;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_boll_parallel(continued,floatingRate);

        String str = String.format("Boll走平(continued=%s,floatingRate=%s)",continued,floatingRate);
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * 趋势向上 MA11、M31、MA63
     */
    @Test
    public void TrendUp_ma11_ma31_ma63(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA11,MovingAverage.MA31,MovingAverage.MA63,5,15,30);
        System.out.println("多头排列(MA11、MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("多头排列(MA18、MA31、MA63)",results,true);
    }

    /**
     *
     */
    @Test
    public void test(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,30);
        System.out.println("MA63趋势向上"+results.size());
        FileUtil.writeTxtFile("MA63趋势向上",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        // 参数组1
        //results = trendAnalyzer.get_long_k_stick(8,8, KStickPosition.HALF);

        // 参数组2
        results = trendAnalyzer.get_long_k_stick(8,5, KStickPosition.HALF);

        FileUtil.writeTxtFile("长K棒",results,true);
    }

//    @Test
//    public void test3(){
//        List<String> paths = null;
//        TrendAnalyzer trendAnalyzer = null;
//        List<String> results = null;
//
//        paths = FileStockDailyData.getStockFilesWithFullPath();
//        trendAnalyzer = new TrendAnalyzer(paths);
//        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,30);
//        System.out.println("MA63趋势向上"+results.size());
//        FileUtil.writeTxtFile("MA63趋势向上",results,true);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.get_long_k_stick(5);
//        FileUtil.writeTxtFile("长K棒",results,true);
//    }
}
