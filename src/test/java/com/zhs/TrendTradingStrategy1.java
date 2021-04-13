package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 趋势交易策略1：
 * - 年线向上
 * - 价在年线下
 * - 价在3天内向上突破18MA、31MA和250MA
 */
public class TrendTradingStrategy1 {
    private final String str_year_up = "年线向上";
    private String str_price_under_year = "价在年线下";
    private String str_price_overgo = "价突破三条均线";

    /**
     * 年线上升的股票
     */
    private List<String> ma250_up(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();
        return results;
    }

    /**
     * 获得年线向上的股票
     */
    @Test
    public void test_ma250_up(){
        List<String> results = this.ma250_up();
        FileUtil.writeTxtFile("年线向上",results,true);
        System.out.println(String.format("%s数量：%s",str_year_up,results.size()));
    }

    /**
     * 价在年线下的
     */
    @Test
    public void test_price_under_ma250(){
        List<String> results = this.ma250_up();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        List<String> results1 = trendAnalyzer.get_price_under_ma(MovingAverage.MA250);
        FileUtil.writeTxtFile(str_price_under_year,results1,true);

        System.out.println(String.format("%s数量：%s",str_year_up,results.size()));
        System.out.println(String.format("%s数量：%s",str_price_under_year,results1.size()));
    }

    /**
     * 价在3天内向上突破18MA、31MA和250MA
     */
    @Test
    public void test_price_overgo() {
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();

        trendAnalyzer = new TrendAnalyzer(results);
        List<String> results1 = trendAnalyzer.get_price_overgo(3, MovingAverage.MA18, MovingAverage.MA31, MovingAverage.MA250);
        FileUtil.writeTxtFile(str_price_overgo, results1, true);

        System.out.printf("%s数量：%s%n",str_year_up,results.size());
        System.out.printf("%s数量：%s%n",str_price_overgo,results1.size());
    }
}
