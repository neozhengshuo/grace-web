package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势可能反转之前
 */
public class TrendTradingStrategy6 {

    /**
     * 【趋势在可能反转之[前]-短期趋势比较】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
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
        float distance = 0.06F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(midMa,longMa);

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
     * 【趋势在可能反转之[前]-短期趋势比较】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
     * - 短期趋势在中期趋势和长期趋势之间。
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
        float distance = 0.08F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendBetween(longMa,shortMa,midMa);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 【趋势在可能反转之[前]-短期趋势比较】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
     * - 短期趋势在中期趋势和长期趋势之间。
     */
    @Test
    public void test4() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
        float distance = 0.08F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getPriceUnderMa(MovingAverage.MA144);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 【趋势在可能反转之[前]-短期趋势比较】
     * - 长周期均线、中周期均线向上
     * - 中周期均线在长周期均线下方
     * - 中周期均线和长周期均线之间的距离在0.05-0.08（取值范围）。
     * - [价格]在中期趋势和长期趋势之间。
     */
    @Test
    public void test5() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        /* 参数组 */
        MovingAverage shortMa = MovingAverage.MA5;
        MovingAverage midMa = MovingAverage.MA44;
        MovingAverage longMa = MovingAverage.MA144;
        float distance = 0.12F;

        /* 筛选 */
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(midMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionBelow(midMa,longMa);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(midMa,longMa,distance);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getPriceBetweenMa(longMa,midMa);

        String strOut = String.format("EMA(%s %s %s)_D(%s)",
                shortMa.getMaValue(),
                midMa.getMaValue(),
                longMa.getMaValue(),
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void temp() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA44);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA5);

        String strOut = "test";

        FileUtil.writeTxtFile(strOut, results, true);
    }

}

