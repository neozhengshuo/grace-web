package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 交易策略，均线系统
 */
public class TrendTradingStrategy6 {

    /**
     * 1. 长周期均线、中周期均线向上
     * 2. 端周期均线在长周期均线和中周期均线中间
     * 3. 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
     */
    @Test
    public void test1() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组1 */
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA30;
        MovingAverage longMa = MovingAverage.MA250;
        float distance = 0.06F;

        /* 参数组2
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
        float distance = 0.05F;
         */

        /* 第1组 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA44);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA144);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getTrendBetween(MovingAverage.MA144, MovingAverage.MA5,MovingAverage.MA44);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(MovingAverage.MA44,MovingAverage.MA144,0.05F);

        /* 第2组 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(shortMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getTrendBetween(longMa,shortMa,midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 1. 长周期均线、中周期均线向上
     * 2. 中周期均线在长周期均线下方
     * 3. 短周期均线在长周期均线和中周期均线中间
     * 4. 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
     */
    @Test
    public void test2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组1 */
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
        float distance = 0.06F;

        /* 参数组2
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
        float distance = 0.05F;
         */

        /* 第1组 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_ema_down_ema(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_ema_down_ema(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getTrendBetween(longMa, shortMa,midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
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

