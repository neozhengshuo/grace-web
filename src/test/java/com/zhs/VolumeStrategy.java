package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VolumeStrategy {
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
}
