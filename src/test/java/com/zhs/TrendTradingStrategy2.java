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
 * - 价在年线上
 * -
 */
public class TrendTradingStrategy2 {
    private final String str_year_up = "年线向上";
    private String str_price_up_year = "价在年线上";

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
     * 价在年线上的
     */
    @Test
    public void test_price_up_ma250(){
        List<String> results = this.ma250_up();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(results);
        List<String> results1 = trendAnalyzer.get_price_up_ma(MovingAverage.MA250);
        FileUtil.writeTxtFile(str_price_up_year,results1,true);

        System.out.println(String.format("%s数量：%s",str_year_up,results.size()));
        System.out.println(String.format("%s数量：%s",str_price_up_year,results1.size()));
    }
}
