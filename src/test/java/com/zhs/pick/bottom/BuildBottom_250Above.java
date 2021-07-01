package com.zhs.pick.bottom;

import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 发现筑底的股票，价格在250MA之下的。
 */
public class BuildBottom_250Above {

    @Test
    public void findBottom1(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 3;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        //  均线结构
        //
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(63,250);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(31,250);

        // 均线距离
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.01F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,0.1F);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(5,250,0.02F);

        // 价格位置
        //
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUnderMa(250);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void findBottom2(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 3;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        //  均线结构
        //
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(63,250);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(31,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        // 均线距离
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.02F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,0.1F);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(5,250,0.02F);

        // 价格位置
        //
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceUnderMa(250);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void findBottom3(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 3;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        //  均线结构
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        // 均线距离
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.02F);

        // 价格位置
        //
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
