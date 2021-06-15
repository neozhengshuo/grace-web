package com.zhs;

import com.zhs.analysis.*;
import com.zhs.analysis.shape.VolumeShrinkageShapeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Kdj;
import com.zhs.entities.dict.RedGreen;
import com.zhs.indicator.DIndicator;
import com.zhs.indicator.KIndicator;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

import java.util.ArrayList;
import java.util.List;

public class ShapeAnalyzerStrategy {
    @Test
    public void test(){
        List<String> results = new ArrayList<>();
        results = FileStockDailyData.getStockFilesWithFullPath();

        /**
         * 确定标的后，在30分钟上按平台交易操作。
         */
        int days = 4;
        float abovePricePercentage = 1.2F;
        float underPricePercentage = 1F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();

//        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results);
//        results = shapeAnalyzer.analyzer();

        String strOut = "test";
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
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendDown(5);



        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * test
     */
    @Test
    public void test2(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();


        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKLow(15);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        String strOut = "test2";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * test
     */
    @Test
    public void test3(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(5);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(10);

        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);



//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
//        results = volumeAnalyzer.getLowVolume(5,63);

        String strOut = "test2";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * test
     */
    @Test
    public void test4(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 趋势向上。
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        // 价格扣抵：价格在31日上、10日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(10);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);


        String strOut = "test2";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * test
     */
    @Test
    public void test5(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 趋势向上。
        //
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getLowVolume(5,63);

        String strOut = "test2";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
