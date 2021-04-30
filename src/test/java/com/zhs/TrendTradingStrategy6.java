package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.datasource.FileStockDailyDataTest;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TrendTradingStrategy6 {
    @Test
    public void test(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,10);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_ema_up(MovingAverage.MA30,5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA30);

        String strOut = String.format("EMA(%s)向上(%s)",MovingAverage.MA30.getMaValue(),5);
        FileUtil.writeTxtFile(strOut,results,true);
    }

    @Test
    public void test2(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
//        trendAnalyzer = new TrendAnalyzer(paths);
//        results = trendAnalyzer.get_ema_up(MovingAverage.MA30,20);

        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_ema_golden_fork(MovingAverage.MA5,MovingAverage.MA30);

        String strOut = String.format("EMA(%s)向上(%s)",MovingAverage.MA30.getMaValue(),5);
        FileUtil.writeTxtFile(strOut,results,true);
    }

    /**
     * 年线向上，价突破30EMA。
     */
    @Test
    public void test3(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA63);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA31);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.get5MaTrendDown();
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getUpperShadow();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA30);

        String strOut = "年线向上价突破30EAM";
        FileUtil.writeTxtFile(strOut,results,true);
    }

    /**
     * 价突破30EMA，并且价在63MA上方
     */
    @Test
    public void test4(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_price_up_ma(MovingAverage.MA63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA30);

        String strOut = "年线向上价突破30EAM";
        FileUtil.writeTxtFile(strOut,results,true);
    }

    /**
     * 价突破30EMA，63MA、250MA向上
     */
    @Test
    public void test5(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(paths);
        results =  trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,10,40);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA30);

        String strOut = "年线向上价突破30EAM";
        FileUtil.writeTxtFile(strOut,results,true);
    }
}
