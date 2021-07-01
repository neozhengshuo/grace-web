package com.zhs.strategies.trend;

import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MovingAverage_Tangled_250Under {

    private List<String> getTrendUp(){
        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,250);


        return results;
    }

    @Test
    public void trendUp(){
        List<String> results = getTrendUp();

        float distance = 0.03F;
        TrendAnalyzer trendAnalyzer;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,distance);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,distance);

        String strOut = "MovingAverage_Tangled_250Under."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp(){
        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaGoldCross(31,63);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp2(){
        TrendAnalyzer trendAnalyzer;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaGoldCross(5,31);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp3(){
        TrendAnalyzer trendAnalyzer;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaGoldCross(5,63);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp4(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 3;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(5,days);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(31,days);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(63,days);
        priceAnalyzer = new PriceAnalyzer(results);

//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
//        results = volumeAnalyzer.


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp5(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 3;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.01F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,0.03F);


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
