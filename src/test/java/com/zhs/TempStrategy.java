package com.zhs;

import com.zhs.analysis.KDAnalyzer;
import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TempStrategy {
    @Test
    public void test(){
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 中长周期
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,31);

        // 短周期
        //

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test2(){
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 趋势
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(18);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(11);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(18,31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(11,18);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(5,11);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(5,250);

        // 量
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);


        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test3(){
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        //量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        //
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(1);

        String strOut = "test2";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
