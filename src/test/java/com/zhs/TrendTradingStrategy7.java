package com.zhs;

import com.zhs.analysis.KLineAnalyzer;
import com.zhs.analysis.ShapeAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.entities.dict.RedGreen;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势反转之后
 */

public class TrendTradingStrategy7 {
    /**
     * 【趋势反转[后]-均线距离比较】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.01-0.09（取值范围）。
     */
    @Test
    public void test1() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        int shortMa = 5;
        int midMa = 44;
        int longMa = 144;
        float distance = 0.03F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa,
                midMa,
                longMa,
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 【趋势在可能反转之[后]-短期趋势在中期趋势和长期趋势之间】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.01-0.09（取值范围）。
     * - 短期趋势在中期趋势和长期趋势之间
     */
    @Test
    public void test2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        int shortMa = 5;
        int midMa = 44;
        int longMa = 144;
        float distance = 0.06F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendBetween(midMa,shortMa,longMa);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa,
                midMa,
                longMa,
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 【趋势在可能反转之[后]-[价]在中期趋势和长期趋势之间】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.01-0.09（取值范围）。
     * - [价]在中期趋势和长期趋势之间
     */
    @Test
    public void test3() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        int shortMa = 5;
        int midMa = 44;
        int longMa = 144;
        float distance = 0.1F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getPriceBetweenMa(midMa,longMa);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa,
                midMa,
                longMa,
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 均线距离
        float distance = 0.001F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,63,distance);

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        /* 筛选 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(31);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(5,31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,144,distance);

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP3() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,distance);

        VolumeAnalyzer volumeStrategy = new VolumeAnalyzer(results);
        results = volumeStrategy.getExpandVolume2(RedGreen.RED,7);

        KLineAnalyzer klineAnalyzer = new KLineAnalyzer(results);
        results = klineAnalyzer.getLongKline(7,4F);


        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP4() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(63,250,0.002F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(31,250,0.004F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

//        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,6,1.5F,0.3F);
//        results = shapeAnalyzer.analyzer();


        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP5() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(10);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(5,10);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(10,20);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(20,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(10,20,distance);

        VolumeAnalyzer volumeStrategy = new VolumeAnalyzer(results);
        results = volumeStrategy.getExpandVolume2(RedGreen.RED,7);

        KLineAnalyzer klineAnalyzer = new KLineAnalyzer(results);
        results = klineAnalyzer.getLongKline(7,4F);


        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 均线纠结
     */
    @Test
    public void TEMP6() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,10,0.002F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(10,20,0.004F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(20,63);

        VolumeAnalyzer volumeStrategy = new VolumeAnalyzer(results);
        results = volumeStrategy.getExpandVolume2(RedGreen.RED,7);

        KLineAnalyzer klineAnalyzer = new KLineAnalyzer(results);
        results = klineAnalyzer.getLongKline(7,4F);

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 多周期均线向上
     */
    @Test
    public void TEMP7() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        /* 筛选 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(30);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(63);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(250);

//        int days = 1;
//        int days = 3;
//        int days = 4;
//        int days = 5;
        int days = 6;
//        int days = 7;
//        int days = 8;
        float abovePricePercentage = 1.5F;
        float underPricePercentage = 0.30F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
