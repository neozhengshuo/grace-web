package com.zhs.strategy;

import com.zhs.analysis.Analyzer;
import com.zhs.entities.dict.Boll;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StrategiesTest {
    private static final Logger logger = LoggerFactory.getLogger(StrategiesTest.class);

    @Test
    public void trendTracking(){
        Analyzer analyzer = new Analyzer();
        List<String> files = FileUtil.getStockFilesWithFullPath();
        List<String> result1 = analyzer.getTrendUpwards(files,250,50);
        List<String> result2 = analyzer.getTrendUpwards(result1,63,20);
        List<String> result3 = analyzer.getTrendUpwards(result2,31,10);
        List<String> result4 = analyzer.getBoll(result3, Boll.MID);

        FileUtil.writeTxtFile("趋势向上",result4,true);

        printResult(result4);
        logger.info(String.format("股票总数：%s",files.size()));
        logger.info(String.format("命中数量：%s",result4.size()));
    }

    @Test
    public void trendUp(){
        Analyzer analyzer = new Analyzer();
        List<String> files = FileUtil.getStockFilesWithFullPath();
        List<String> result1 = analyzer.getTrendUpwards(files,250,50);
        List<String> result2 = analyzer.getTrendUpwards(result1,63,20);
        List<String> result3 = analyzer.getTrendUpwards(result2,31,10);

        FileUtil.writeTxtFile("趋势向上",result3,true);
    }

    private void printResult(List<String> nameList){
        for(String name:nameList){
            logger.info(name);
        }
    }
}
