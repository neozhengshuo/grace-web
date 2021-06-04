package com.zhs;
import com.zhs.analysis.KLineAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VolumePriceStrategy {
    @Test
    public void test(){
        TrendAnalyzer trendAnalyzer = null;
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        VolumeAnalyzer volumeStrategy = new VolumeAnalyzer(results);
        results = volumeStrategy.getExpandVolume(5,2.5F);

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

    @Test
    public void test1(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        KLineAnalyzer klineAnalyzer = new KLineAnalyzer(results);
        results = klineAnalyzer.getLongKline(5,5F);

        VolumeAnalyzer volumeStrategy = new VolumeAnalyzer(results);
        results = volumeStrategy.getExpandVolume(5,1.8F);

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
