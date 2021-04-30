package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势交易策略3：
 * - ma31、ma63、ma250向上
 */
public class TrendTradingStrategy4 {
    @Test
    public void test_18ma_31ma_ma250_up(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        //results = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,MovingAverage.MA250,8,20,50);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA63,MovingAverage.MA250,15,40);
        //results = trendAnalyzer.getTrendUp(MovingAverage.MA250,50);
        System.out.println("ma31_ma63_ma250："+results.size());
        FileUtil.writeTxtFile("ma31_ma63_ma250",results,true);
    }
}
