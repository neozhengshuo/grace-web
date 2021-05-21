package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 平台策略
 */
public class PlatformStrategy1 {
    @Test
    public void Test1(){
        List<String> paths = null;
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        // 参数组1
//        int days = 60;
//        float distance = 0.16F;

        // 参数组2
//        int days = 60;
//        float distance = 0.05F;

        // 参数组3
//        int days = 50;
//        float distance = 0.05F;

        // 参数组3
        int days = 20;
        float distance = 0.03F;

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getPlatform(days,distance);

        String strOut = String.format("platform(%s %s)",
                days,
                distance);
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
