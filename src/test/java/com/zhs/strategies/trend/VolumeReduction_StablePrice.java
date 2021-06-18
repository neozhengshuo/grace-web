package com.zhs.strategies.trend;

import com.zhs.analysis.KDAnalyzer;
import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;


/**
 * 趋势向上，量缩价稳
 */
public class VolumeReduction_StablePrice {
    private List<String> results = new ArrayList<>();

    /*
    * 趋势向上
    * */
    @BeforeEach
    public void trendDetermineTest(){
        this.results = FileStockDailyData.getStockFilesWithFullPath();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(this.results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(250);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);
    }


    /**
     * 价在18日线上，5日线下
     */
    @Test
    public void test1(){
        // 趋势判定
        //
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(this.results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        // 价格扣抵：价格在18、31、63日上。5日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(18);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionUnder(5);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // J值在低档向上
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, this.results, true);
    }

    /**
     * 价格在31日上、10日下。
     */
    @Test
    public void test2(){
        // 价格扣抵：价格在31日上、10日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionUnder(18);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // J值在低档向上
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 价格扣抵：价格在63日上、31日下。
     */
    @Test
    public void test3(){
        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test4(){
        // 价格扣抵：价格在63日上、31日下。
        //
//        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
//        results = priceAnalyzer.getPositionAbove(63);
//
//        priceAnalyzer = new PriceAnalyzer(this.results);
//        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getLowVolume(5,63);

        // KDJ在指定的值之下。
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getKdjLow(50,50,0);

        kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(0);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test6(){
        // 价格扣抵：价格在63日上、31日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(63);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
//        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
//        results = volumeAnalyzer.getLowVolume(5,63);

        // KD的K在低档
        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(10);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp(){
        // 价格扣抵：价格在63日上、31日下。
        //
//        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
//        results = priceAnalyzer.getPositionAbove(63);
//
//        priceAnalyzer = new PriceAnalyzer(this.results);
//        results = priceAnalyzer.getPositionUnder(31);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getVolumeAbove(5,63);


        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
        results = kdAnalyzer.getJUp(30);

//        KDAnalyzer kdAnalyzer = new KDAnalyzer(results);
//        results = kdAnalyzer.getJUpWithVolume(0);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    @AfterEach
    public void outputResults(){
        String outTxt = "results";
        FileUtil.writeTxtFile(outTxt, this.results, true);
    }
}
