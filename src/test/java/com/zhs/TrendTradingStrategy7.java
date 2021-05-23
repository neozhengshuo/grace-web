package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势反转之后
 */

public class TrendTradingStrategy7 {
    /**
     * 【趋势在可能反转之[后]-均线距离比较】
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
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
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
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
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
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
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
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
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
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
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
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void TEMP() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA31;
        MovingAverage longMa = MovingAverage.MA63;
        float distance = 0.001F;

        /* 筛选 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(midMa);
//
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(shortMa);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaPositionAbove(midMa,longMa);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(shortMa,longMa,distance);



        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(shortMa,longMa,distance);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
