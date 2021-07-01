package com.zhs.pick.bottom;

import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BuildBottom_250Under {
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
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(11,250);


        // 均线距离
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(11,31,0.015F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,63,0.015F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,250,0.05F);

//        // 价格位置
//        //
//        priceAnalyzer = new PriceAnalyzer(results);
//        results = priceAnalyzer.getCurrentPriceUnderMa(63);
//        priceAnalyzer = new PriceAnalyzer(results);
//        results = priceAnalyzer.getCurrentPriceAboveMa(250);
//        priceAnalyzer = new PriceAnalyzer(results);
//        results = priceAnalyzer.getPriceAndMaDistance(250,0.02F);


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
