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
     * 1. 找到趋势向上的股票（63MA、250MA向上的股票）
     */
    @Test
    public void trendUp(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,20,50);
        System.out.println("趋势向上(63MA、250MA)："+results.size());
        FileUtil.writeTxtFile("趋势向上(63MA、250MA)",results,true);
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
}
