package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势交易策略3：
 * - ma18、ma31、ma250向上
 */
public class TrendTradingStrategy3 {
    @Test
    public void test_18ma_31ma_ma250_up(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA18,MovingAverage.MA31,MovingAverage.MA250,10,25,35);
        System.out.println("ma18_ma31_ma250："+results.size());
        FileUtil.writeTxtFile("ma18_ma31_ma250",results,true);
    }
}
