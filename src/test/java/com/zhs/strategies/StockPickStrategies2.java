package com.zhs.strategies;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 主线：强势向上（18MA、31MA、63MA）
 */
public class StockPickStrategies2 {

    /**
     * 18MA、31MA、63MA向上
     * @return
     */
    public List<String> getTrendUp_MA18_MA31_MA63(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.getTrendUp(MovingAverage.MA18,MovingAverage.MA31,MovingAverage.MA63,10,25,50);
        System.out.println("多头排列(MA18、MA31、MA63)："+results.size());
        FileUtil.writeTxtFile("强势向上(MA18、MA31、MA63)",results,true);
        return results;
    }

    /**
     * 18MA、31MA、63MA向上
     */
    @Test
    public void getTrendUp(){
        List<String> results = this.getTrendUp_MA18_MA31_MA63();
    }

    /**
     * 趋势向上，价来到31MA下方
     */
    @Test
    public void strategies_price_is_under_31ma(){
        List<String> trendUpList = this.getTrendUp_MA18_MA31_MA63();

        // 参数
        //
        MovingAverage ma = MovingAverage.MA31;

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(trendUpList);
        List<String> results = trendAnalyzer.get_price_under_ma(ma);
        String str = String.format("价来的%sMA的下方",ma.getMaValue());
        FileUtil.writeTxtFile(str,results,true);
    }

    /**
     * 趋势向上中，股价来到18MA和31MA的中间
     */
    @Test
    public void strategies_price_is_between_ma(){
        List<String> trendUpList = this.getTrendUp_MA18_MA31_MA63();

        // 参数
        //
        MovingAverage up_ma = MovingAverage.MA18;
        MovingAverage low_ma = MovingAverage.MA31;

        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(trendUpList);
        List<String> results = trendAnalyzer.get_is_price_between_ma(up_ma,low_ma);
        String str = String.format("价在%sMA和%sMA的中间",up_ma.getMaValue(),low_ma.getMaValue());
        FileUtil.writeTxtFile(str,results,true);
    }

}
