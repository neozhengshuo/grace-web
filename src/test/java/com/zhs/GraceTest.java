package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.StockFeatures;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GraceTest {

    /**
     * 年线上升的股票
     */
    private List<String> ma250_up(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();
        return results;
    }

    /**
     * 获得年线上升的股票
     */
    @Test
    public void test_ma250_up(){
        List<String> results = this.ma250_up();
        FileUtil.writeTxtFile("年线向上",results,true);
    }

    /**
     * 价在年线上的
     */
    @Test
    public void test_price_under_ma250(){
        String str = "年线向上的";
        String str1 = "价在年线上的";
        List<String> results = this.ma250_up();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        List<String> results1 = trendAnalyzer.get_price_up_ma(MovingAverage.MA250);
        FileUtil.writeTxtFile(str,results1,true);

        System.out.println(String.format("%s数量：%s",str,results.size()));
        System.out.println(String.format("%s数量：%s",str1,results1.size()));
    }

    /**
     * 价在年线下的
     */
    @Test
    public void test_price_up_ma250(){
        List<String> results = this.ma250_up();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_under_ma(MovingAverage.MA250);
        FileUtil.writeTxtFile("价在年线下",results,true);
    }

    /**
     * 1. 找到趋势向上的股票（MA31、63MA、250MA向上的股票）
     */
    @Test
    public void trendUp(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        //results = trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,20,50);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,MovingAverage.MA250,8,20,50);
        System.out.println("趋势向上(MA31、63MA、250MA)："+results.size());
        FileUtil.writeTxtFile("趋势向上(MA31、63MA、250MA)",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        float jValue = -2;
//        results = trendAnalyzer.getJLow(jValue);
//        FileUtil.writeTxtFile(String.format("J值在%s的下方",jValue),results,true);
    }

    /**
     * 2. 价在31MA和63MA之间。
     */
    @Test
    public void grace1(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,20,50);
        System.out.println("趋势向上(63MA、250MA)："+results.size());
        //FileUtil.writeTxtFile("趋势向上(63MA、250MA)",results,true);

        trendAnalyzer = new TrendAnalyzer(results);

        // 参数组1
        results = trendAnalyzer.get_is_price_between_ma(MovingAverage.MA31,MovingAverage.MA63);
        FileUtil.writeTxtFile("价在31MA和63MA之间",results,true);
    }

    /**
     * 2. 价在5MA和31MA之间。
     */
    @Test
    public void grace2(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,20,50);
        System.out.println("趋势向上(63MA、250MA)："+results.size());
        //FileUtil.writeTxtFile("趋势向上(63MA、250MA)",results,true);

        trendAnalyzer = new TrendAnalyzer(results);

        // 参数组1
        results = trendAnalyzer.get_is_price_between_ma(MovingAverage.MA5,MovingAverage.MA31);
        FileUtil.writeTxtFile("价在5MA和31MA之间",results,true);
    }

    /**
     * 1. 18MA,63MA,250MA向上
     */
    @Test
    public void trendUp1(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA18,MovingAverage.MA31,MovingAverage.MA250,5,10,30);
        System.out.println("趋势向上(MA18)："+results.size());
        FileUtil.writeTxtFile("趋势向上(MA18)",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_is_price_between_ma(MovingAverage.MA18,MovingAverage.MA31);
        FileUtil.writeTxtFile("价在18MA和31MA之间",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        float jValue = -2;
//        results = trendAnalyzer.getJLow(jValue);
//        FileUtil.writeTxtFile(String.format("J值在%s的下方",jValue),results,true);
    }

    @Test
    public void temp(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();
        System.out.println("年线向上"+results.size());
        FileUtil.writeTxtFile("年线向上",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_under_ma(MovingAverage.MA250);
        FileUtil.writeTxtFile("价在18MA和31MA之间",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        float jValue = -2;
//        results = trendAnalyzer.getJLow(jValue);
//        FileUtil.writeTxtFile(String.format("J值在%s的下方",jValue),results,true);
    }

    @Test
    public void temp4(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();
        System.out.println("年线向上"+results.size());
        FileUtil.writeTxtFile("年线向上",results,true);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_overgo(3,MovingAverage.MA18,MovingAverage.MA31,MovingAverage.MA250);
        FileUtil.writeTxtFile("价穿越3条均线",results,true);

//        trendAnalyzer = new TrendAnalyzer(results);
//        float jValue = -2;
//        results = trendAnalyzer.getJLow(jValue);
//        FileUtil.writeTxtFile(String.format("J值在%s的下方",jValue),results,true);
    }
}
