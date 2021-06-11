package com.zhs;

import com.zhs.analysis.KLineAnalyzer;
import com.zhs.analysis.ShapeAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.RedGreen;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShapeAnalyzerStrategy {
    @Test
    public void test(){
        List<String> results = FileStockDailyData.getStockFilesWithFullPath();

        TrendAnalyzer trendAnalyzer = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.001F;


        /* 筛选 */
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(18);
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaTrendUp(31);
//
//
//        trendAnalyzer = new TrendAnalyzer(results);
//        results = trendAnalyzer.getMaDistance(5,144,distance);

        /**
         * 确定标的后，在30分钟上按平台交易操作。
         */
//        int days = 3;
//        float abovePricePercentage = 1.5F;
//        float underPricePercentage = 0.95F;
//        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
//        results = shapeAnalyzer.analyzer();

        /**
         * 确定标的后，
         * 观察：
         * 长红K后一天的K线要收出长上影线（上影线大于下影线）、价涨（红K）、收盘价站上长红K的收盘价。
         * 查看长红K棒分时图中的起涨点（放量上涨时的位置）的价位。
         * 分时图形态为：
         * 【入场形态】开盘后股价下跌，然后出现较大幅拉升（收集筹码），拉升后股价回撤（呈现量缩价稳），
         *            但回撤没有来到开盘时价格下跌的区间，且回撤后股价又开始拉升（收集筹码），
         *            最后收盘价高于拉升后回撤的价格。
         *            在一天的分时图中，回撤/拉升可能有几个来回（都是收集筹码），但最后收盘价要等于或高于开盘价。该形态的特点是股价下跌/回撤的过程中有人收集筹码。）
         *            收集筹码大多发生在股价下跌后企稳（因为这时较容易）。
         * 【入场形态】开盘后股价下跌，没有立即出现收集筹码的动作，如果当天的股价在低位走稳，且量能没有放大，在下午尾盘时出现收集筹码的动作（放量拉升，收下影线）。
         * 【入场形态】开盘后股价上涨（收集筹码），当天股价下跌到低位后企稳，量能缩小，这时收集筹码的行为在第二天（收上影线）
         * 【入场形态】分时图中价格支撑逐步上移，回撤不过前低，尾板有拉升。
         * 【入场形态】开盘后股价直接拉升，然后开始回撤，回撤途中没有出现明显的拉升，最后收盘价高于开盘价。
         * 【入场形态】尾盘有拉升
         * 【入场形态】分时图中的下跌放大量。
         * 【出场形态】开盘后股上上下下波动激烈，仔细观察还是卖方力量强大一些。
         * 【出场形态】开盘后股价拉升出现回调，但没有救场行为（收集筹码）。
         * 【出场形态】跌深的反弹，收出下影线。
         * 【量大价不涨】跌深的反弹，收出下影线。
         * 手工计算长红K的成本价：
         * 在分时图上识别主力买点，并进行计算：密集成交区，量大拉升起点。
         */
        int days = 2;
        float abovePricePercentage = 1.1F;
        float underPricePercentage = 0.95F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();


        /**
         * 当天出现长红K
         * 找到下跌途中放量长红，在分时图中有密集成交区域，量大且拉升幅度大，密集成交区后价格虽有回落但量巨稳，且上方没有套牢盘
         * 【有上影线的，不要涨停的，近期高点的不要】
         */
//        int days = 1;
//        float abovePricePercentage = 1.1F;
//        float underPricePercentage = 0.95F;
//        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
//        results = shapeAnalyzer.analyzer();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 趋势向上，回调形态
     * 量缩价稳入场。
     */
    @Test
    public void TEMP2() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

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
     * 趋势向上，回调形态
     * 量缩价稳入场。
     */
    @Test
    public void TEMP3() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,31,distance);

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 趋势向上，回调形态
     * 量缩价稳入场。
     */
    @Test
    public void TEMP4() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,144);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,63,distance);

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    /**
     * 趋势向上，回调形态
     * 量缩价稳入场。
     */
    @Test
    public void TEMP5() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float distance = 0.002F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaTrendUp(31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,63);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(5,31);

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaDistance(5,63,31);

        String strOut = "test1";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
