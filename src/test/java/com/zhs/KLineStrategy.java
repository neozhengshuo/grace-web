package com.zhs;

import com.zhs.analysis.KLineAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KLineStrategy {
    @Test
    public void test(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        KLineAnalyzer volumeStrategy = new KLineAnalyzer(results);
        results = volumeStrategy.getLongKline(5,10F);

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
