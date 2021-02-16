package com.zhs.analysis;

import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TrendAnalyzerTest {
    private static final TrendAnalyzer trendAnalyzer;

    static {
        List<String> fileList = FileUtil.getStockFilesWithFullPath();
        trendAnalyzer = new TrendAnalyzer(fileList);
    }

    public TrendAnalyzerTest(){

    }

    @Test
    public void getBollMidTest(){
        List<String> results = trendAnalyzer.getBollMid();

        FileUtil.writeTxtFile("趋势向上-布林中轨",results,true);
        results.forEach(System.out::println);
        System.out.printf("getBollMid: %s%n",results.size());
    }

    @Test
    public void getKDJTest(){
        List<String> results = trendAnalyzer.getKDJ();

        FileUtil.writeTxtFile("趋势向上-J值上涨",results,true);
        results.forEach(System.out::println);
        System.out.printf("getKDJ: %s%n",results.size());
    }
}
