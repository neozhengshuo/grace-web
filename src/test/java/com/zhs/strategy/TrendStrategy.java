package com.zhs.strategy;

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

public class TrendStrategy {
    private List<String> results = new ArrayList<>();

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

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, this.results, true);
    }

    /**
     * test
     */
    @Test
    public void test2(){
        // 价格扣抵：价格在31日上、10日下。
        //
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionAbove(31);

        priceAnalyzer = new PriceAnalyzer(this.results);
        results = priceAnalyzer.getPositionUnder(10);

        // 量缩：量小于5日、63日均量。
        //
        VolumeAnalyzer volumeAnalyzer = new VolumeAnalyzer(this.results);
        results = volumeAnalyzer.getLowVolume(5,63);

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * test **
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

//        String strOut = "test2";
//        FileUtil.writeTxtFile(strOut, results, true);
    }

    @AfterEach
    public void outputResults(){
        String outTxt = "results";
        FileUtil.writeTxtFile(outTxt, this.results, true);
    }
}
