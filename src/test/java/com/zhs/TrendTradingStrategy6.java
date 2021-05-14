package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TrendTradingStrategy6 {

    /**
     * 短周期均线在长周期均线下方，且短周期均线向上。
     */
    @Test
    public void test2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 第0组 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getTrendBetween(MovingAverage.MA144, MovingAverage.MA5,MovingAverage.MA44);

//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA144);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA44);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaGoldCross(MovingAverage.MA5,MovingAverage.MA30);


        /* 第一组  EMA144下行，EMA44上行，均线收拢
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA44);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(MovingAverage.MA44,MovingAverage.MA144,0.05F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ma(MovingAverage.MA44);
        */

        /* 第二组
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA44);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(MovingAverage.MA44,MovingAverage.MA144,0.03F);
        */

        String strOut = String.format("EMA(%s)向上(%s)", MovingAverage.MA30.getMaValue(), 5);
        FileUtil.writeTxtFile(strOut, results, true);

    }

    /**
     * 价站上EMA144/169EMA。
     */
    @Test
    public void test3() {
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA144);

        String strOut = "年线向上价突破30EAM";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * EMA144/169EMA向上,EMA20向下
     * 股价接近EMA144，且有下影线
     */
    @Test
    public void test6() {
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA169);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA20);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ema(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getLowerShadow();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}

