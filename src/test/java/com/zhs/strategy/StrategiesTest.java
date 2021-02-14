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

    /**
     * 趋势跟踪，观察买入信号
     */
    @Test
    public void trendTracking(){
        Analyzer analyzer = new Analyzer();
        List<String> files = FileUtil.getStockFilesWithFullPath();

        // 获取趋势向上的股票（31MA、63MA、250MA）
        //
        List<String> result1 = analyzer.getTrendUpwards(files,250,50);
        List<String> result2 = analyzer.getTrendUpwards(result1,63,20);
        List<String> result3 = analyzer.getTrendUpwards(result2,31,10);

        // 股价在BOLL中轨以下的
        //
        List<String> result4 = analyzer.getBoll(result3, Boll.MID);

        // 当天股价上涨，前一天股价下跌
        //

        // 前一天5MA向下，当天5MA向上()
        //
        List<String> result5 = analyzer.get5MAUp(result4);

        // 当天成交量大于等于5日均量
        // ...


        FileUtil.writeTxtFile("趋势向上",result5,true);

        printResult(result4);
        logger.info(String.format("股票总数：%s",files.size()));
        logger.info(String.format("命中数量：%s",result4.size()));
    }

    /**
     * 趋势跟踪，关注买入信号
     */
    @Test
    public void trendTrackingTest(){
        Analyzer analyzer = new Analyzer();
        List<String> result = analyzer.trendTracking();
        FileUtil.writeTxtFile("趋势向上",result,true);
    }

    // 效果不好
    //
    @Test
    public void trendUp(){
        Analyzer analyzer = new Analyzer();
        List<String> files = FileUtil.getStockFilesWithFullPath();
        List<String> result1 = analyzer.getTrendUpwards(files,250,50);
        List<String> result2 = analyzer.getTrendUpwards(result1,63,20);
        List<String> result3 = analyzer.getTrendUpwards(result2,31,10);

        FileUtil.writeTxtFile("趋势向上",result3,true);
    }

    @Test
    public void getMovingAverageTangledTest(){
        Analyzer analyzer = new Analyzer();
        List<String> files = FileUtil.getStockFilesWithFullPath();
        List<String> results = analyzer.getMovingAverageTangled(files,10);
        FileUtil.writeTxtFile("均线纠结",results,true);
    }

    private void printResult(List<String> nameList){
        for(String name:nameList){
            logger.info(name);
        }
    }
}
