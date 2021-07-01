package com.zhs.pick.bottom;

import com.zhs.analysis.BollAnalyzer;
import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class tempTest {

    @Test
    public void test(){
        TrendAnalyzer trendAnalyzer;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(63);

        BollAnalyzer bollAnalyzer = new BollAnalyzer(results);
        results = bollAnalyzer.getShrink(0.03F);

//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
//        results = volumeAnalyzer.getVolumeGreater(8,2);

        String strOut = "Test"+Thread.currentThread().getStackTrace()[1].getMethodName();
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
        results = trendAnalyzer.getMaTrendUp(18);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(18,31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        // 均线距离
        //
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(31,63,0.02F);

//        // 价格位置
//        //
//        priceAnalyzer = new PriceAnalyzer(results);
//        results = priceAnalyzer.getCurrentPriceAboveMa(31);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
