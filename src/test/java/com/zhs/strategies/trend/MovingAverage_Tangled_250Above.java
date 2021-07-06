package com.zhs.strategies.trend;

import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MovingAverage_Tangled_250Above {

    private List<String> getTrendUp(){
        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);
//
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(63,250);
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(31,250);


        return results;
    }

    @Test
    public void trendUp(){
        List<String> results = getTrendUp();

        float distance = 0.03F;
        TrendAnalyzer trendAnalyzer;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,63,0.03F);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,31,0.03F);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.03F);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = "MovingAverage_Tangled_250Under"+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void trendUp2(){
        List<String> results = getTrendUp();

        float distance = 0.03F;
        TrendAnalyzer trendAnalyzer;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,63,0.03F);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,31,0.03F);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.03F);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        priceAnalyzer.getCurrentPriceAboveMa(63);

        String strOut = "MovingAverage_Tangled_250Under"+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
