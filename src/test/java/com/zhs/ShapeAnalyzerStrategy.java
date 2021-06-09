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
         * 分时图形态为：
         * 1. 【入场形态】开盘后股价下跌，然后出现较大幅拉升，拉升后股价回撤，但回撤没有来到开盘时价格下跌的区间，且回撤后股价有开始拉升，最后收盘价高于拉升后回撤的价格。
         * 在一天的分时图中，回撤/拉升可能有几个来回，但最后收盘价要等于或高于开盘价。该形态的特点是股价下跌/回撤的过程中有人有救场。
         * 【入场形态】分时图中价格支撑逐步上移，回撤不过前低，尾板有拉升。
         * 2. 【入场形态】开盘后股价直接拉升，然后开始回撤，回撤图中没有出现明显的拉升，最后收盘价等于或高于开盘价。
         * 3. 【出场形态】开盘后股上上下下波动激烈，仔细观察还是卖方力量强大一些。
         * 4. 【出场形态】开盘后股价拉升，但没有救场行为。
         * 5. 【出场形态】跌深的反弹，收出下影线。
         *
         */
        int days = 2;
        float abovePricePercentage = 1.1F;
        float underPricePercentage = 0.95F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
