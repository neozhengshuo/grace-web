package com.zhs.strategies;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 主线：趋势向上（31MA、63MA）
 */
public class StockPickStrategies1 {

    public StockPickStrategies1(){}

    private List<String> getTrendUp_MA31_MA63(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,25,50);
        System.out.println("趋势向上(MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("趋势向上(MA31、MA63)",results,true);
        return results;
    }

    /**
     * 趋势向上中31MA趋于平滑
     */
    @Test
    public void strategies_ma31_smooth(){
        List<String> trendUpList = this.getTrendUp_MA31_MA63();
        // 参数组
        //
        MovingAverage ma = MovingAverage.MA31;
        int continued = 5;
        float floatingRate = 1.5F;

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(trendUpList);
        List<String> results = trendAnalyzer.get_moving_average_smooth(ma,continued,floatingRate);
        String str = String.format("MA31平滑(continued=%s,floatingRate=%s)",continued,floatingRate);
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * 趋势向上中，价来到63MA下方
     */
    @Test
    public void strategies_price_is_under_63ma(){
        List<String> trendUpList = this.getTrendUp_MA31_MA63();

        // 参数
        //
        MovingAverage ma = MovingAverage.MA63;

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(trendUpList);
        List<String> results = trendAnalyzer.get_price_under_ma(ma);
        String str = String.format("价来的%sMA的下方",ma.getMaValue());
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * 趋势向上中，股价来到31MA和63MA的中间
     */
    @Test
    public void strategies_price_is_between_ma(){
        List<String> trendUpList = this.getTrendUp_MA31_MA63();

        // 参数
        //
        MovingAverage up_ma = MovingAverage.MA31;
        MovingAverage low_ma = MovingAverage.MA63;

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(trendUpList);
        List<String> results = trendAnalyzer.get_is_price_between_ma(up_ma,low_ma);
        String str = String.format("价在%sMA和%sMA的中间",up_ma.getMaValue(),low_ma.getMaValue());
        FileUtil.writeTxtFile(str,results,true);
    }

    @Test
    public void temp(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        MovingAverage up_ma = MovingAverage.MA31;
        MovingAverage low_ma = MovingAverage.MA63;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get_is_price_between_ma(up_ma,low_ma);
        String str = String.format("价在%sMA和%sMA的中间",up_ma.getMaValue(),low_ma.getMaValue());
        FileUtil.writeTxtFile(str,results,true);
    }
}
