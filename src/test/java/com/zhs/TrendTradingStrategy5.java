package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TrendTradingStrategy5 {
    @Test
    public void boll_parallel(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;

        paths = FileStockDailyData.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(paths);
        results = trendAnalyzer.get250MaTrendUp();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_price_up_ma(MovingAverage.MA250);


        // 参数组
        int continued = 5;
        float floatingRate = 0.145F;
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.get_boll_parallel(continued,floatingRate);

        String str = String.format("Boll走平(continued=%s,floatingRate=%s)",continued,floatingRate);
        FileUtil.writeTxtFile(str,results,true);
    }
}
