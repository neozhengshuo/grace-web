package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 均线支撑策略/长下影线策略
 */
public class TrendTradingStrategy8 {
    /** 优：*
     * 优选下影线K线，实体大于下影线
     * 只能选择股价从高位向下回调到均线处的，不能选择股价从下方上涨后站上均线后小幅回调的。
     * 要选择大趋势向上，一浪推一浪的。
     * 不能选择收成十字星的。
     */
    @Test
    public void test() {
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA169);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_lower_price_touch_ma(MovingAverage.MA144);

        String strOut = "test";

        FileUtil.writeTxtFile(strOut, results, true);
    }

    /** 优：**
     * 优选下影线K线，实体大于下影线
     * 只能选择股价从高位向下回调到均线处的，不能选择股价从下方上涨后站上均线后小幅回调的。
     * 要选择大趋势向上，一浪推一浪的。
     * 不能选择收成十字星的。
     */
    @Test
    public void test1() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_lower_price_touch_ma(MovingAverage.MA63);

        String strOut = "test";

        FileUtil.writeTxtFile(strOut, results, true);
    }

    /** 优：**
     * 优选下影线K线，实体大于下影线
     * 只能选择股价从高位向下回调到均线处的，不能选择股价从下方上涨后站上均线后小幅回调的。
     * 要选择大趋势向上，一浪推一浪的。
     * 不能选择收成十字星的。
     */
    @Test
    public void test2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA30);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendDown(MovingAverage.MA5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_lower_price_touch_ma(MovingAverage.MA30);

        String strOut = "test";

        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test3() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();


        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(MovingAverage.MA5);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getLowerShadow();

        String strOut = "test";

        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void TEMP() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();


//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getExpandVolume(20,5F);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getLongKline(20,20F);

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
