package com.zhs.analysis;

import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnalyzerTest {
    @Test
    void getTrendUpwardsMa63Ma250Test(){
        Analyzer analyzer = new Analyzer();
        List<String> stocks = analyzer.getTrendUpwardsMa63Ma250(20,50);
        FileUtil.writeTxtWithStockCodeList("趋势向上(63MA,250MA)",stocks,true);

        for(String name:stocks)
            System.out.println(name);
    }

    @Test
    void getTrendUpwardsAndPriceLowerWith63MaTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getTrendUpwardsAndPriceLowerWith63Ma();

        FileUtil.writeTxtWithStockCodeList("趋势向上价格回落-63MA",results,true);

        for(String str:results){
            System.out.println(str);
        }
        System.out.println("命中数量："+results.size());
    }

    @Test
    void getTrendUpwardsAndPriceLowerWith31MaTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getTrendUpwardsAndPriceLowerWith31Ma();

        FileUtil.writeTxtWithStockCodeList("趋势向上价格回落-31MA",results,true);

        for(String str:results){
            System.out.println(str);
        }
        System.out.println("命中数量："+results.size());
    }

    @Test
    void getTrendUpwardsVolumeDropTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getTrendUpwardsVolumeDrop(3);

        FileUtil.writeTxtWithStockCodeList("趋势向上量缩",results,true);

        for(String str:results){
            System.out.println(str);
        }
        System.out.println("命中数量："+results.size());
    }

    @Test
    void maLineGoldCrossTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.maLineGoldCross();

        FileUtil.writeTxtWithStockCodeList("均线金叉",results,true);

        for(String str:results){
            System.out.println(str);
        }
    }

    @Test
    void getStockWithEnlargeVolumeTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getStockWithEnlargeVolume(10);
        FileUtil.writeTxtWithStockCodeList("趋势向上-出现放量",results,true);
        for(String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }

    // 严选：趋势向上（31MA、63MA）,J值在低档、最低价在Boll中轨下方、量小于5日均量
    //
    @Test
    void getTrendUpwardsTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getTrendUpwards();

        // 严选：趋势向上（31MA、63MA）,J值在低档、最低价在Boll中轨下方、量小于5日均量
        //
        FileUtil.writeTxtWithStockCodeList("趋势向上",results,true);
        for(String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }

    @Test
    void sideWaysTest2(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.sideWays(20,0.015);
        FileUtil.writeTxtWithStockCodeList("横盘",results,true);
        System.out.println(results.size());
    }

    @Test
    void movingAverageVolatilityTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.movingAverageVolatility(30,0.04F);
        FileUtil.writeTxtWithStockCodeList("均线浮动率",results,true);
        System.out.println(results.size());
    }

    @Test
    void getTrendUpwardsMa63Ma250WhitMa18DownTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.getTrendUpwardsMa63Ma250WhitMa18Down();
        FileUtil.writeTxtWithStockCodeList("趋势向上（MA63、MA250）MA18下弯",results,true);
        System.out.println(results.size());
    }

    @Test
    void captureRedKTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.captureRedK(3,(float) 0.04);
        FileUtil.writeTxtWithStockCodeList("捕获红K",results,true);
        System.out.println(results.size());
    }

    @Test
    void bollLowerTest(){
        Analyzer analyzer = new Analyzer();
        List<String> results = analyzer.bollLower();

        FileUtil.writeTxtWithStockCodeList("下轨附近",results,true);

        for(String str:results){
            System.out.println(str);
        }
    }
}
