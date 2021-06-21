package com.zhs.rule;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.indicator.JTurningPointIndicator;
import com.zhs.indicator.TrendUpIndicator;
import com.zhs.indicator.VolumeGreaterThanIndicator;
import com.zhs.indicator.VolumeLessThanIndicator;
import org.junit.jupiter.api.Test;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.ProfitLossPercentageCriterion;
//import org.ta4j.core.analysis.criteria

import java.util.List;

public class RuleTest {
    @Test
    public void test(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();
//        BaseBarSeries barSeries = FileStockDailyData.load(results.get(1));
//        System.out.println(barSeries.getName());

//        TrendUpIndicator trendUpIndicator = new TrendUpIndicator(barSeries,31,63,250);
//        VolumeLessThanIndicator volumeLessThanIndicator = new VolumeLessThanIndicator(barSeries,5,63);
//        Rule entryRule = new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63));
//        int endIndex = barSeries.getEndIndex();
//        for(int i = 0;i<endIndex;i++){
//            boolean hit = entryRule.isSatisfied(i);
//            System.out.println(hit + "  "+barSeries.getBar(i).getSimpleDateName());
//        }

        int fileCount = results.size();
        float winCount = 0;
        float loseCount = 0;
        float pinCount = 0;
        for (String file:results){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            Rule entryRule = new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63));
            entryRule.and(new TrendUpRule(new TrendUpIndicator(barSeries,31,63,250)));
            entryRule.and(new JTurningPointRule(new JTurningPointIndicator(barSeries,0)));
            Rule exitRule = new VolumeGreaterThanRule(new VolumeGreaterThanIndicator(barSeries,5,63));

            BaseStrategy baseStrategy = new BaseStrategy(entryRule,exitRule);
            BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
            TradingRecord tradingRecord = barSeriesManager.run(baseStrategy);
//            System.out.println("Number of positions for the strategy: " + tradingRecord.getTrades().size());

            ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
            float percentage = profitLossPercentageCriterion.calculate(barSeries,tradingRecord).floatValue();
//            System.out.println(percentage);

            System.out.println(file+" "+tradingRecord.getTrades().size()+" "+ percentage);
            if(percentage>0F){
                winCount++;
            }else if (percentage<0F){
                loseCount++;
            }else{
                pinCount++;
            }

        }
        System.out.println("WinCount:"+winCount+" "+"loseCount:"+loseCount+" pinCount:"+pinCount);
        System.out.println((winCount/(winCount+pinCount+loseCount))*100);

//        Rule entryRule = new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63));
//        entryRule.and(new TrendUpRule(new TrendUpIndicator(barSeries,31,63,250)));
//        entryRule.and(new JTurningPointRule(new JTurningPointIndicator(barSeries,0)));
//
//        Rule exitRule = new VolumeGreaterThanRule(new VolumeGreaterThanIndicator(barSeries,5,63));
//
//        BaseStrategy baseStrategy = new BaseStrategy(entryRule,exitRule);
//        BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
//        TradingRecord tradingRecord = barSeriesManager.run(baseStrategy);
//        System.out.println("Number of positions for the strategy: " + tradingRecord.getTrades().size());
//
//        ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
//        System.out.println(profitLossPercentageCriterion.calculate(barSeries,tradingRecord).floatValue());

//        int endIndex = barSeries.getEndIndex();
//        for(int i = 0;i<endIndex;i++){
//            if(exitRule.isSatisfied(i)){
//                System.out.println(exitRule.isSatisfied(i)+ "  "+barSeries.getBar(i).getSimpleDateName());
//
//            }
//        }


//        for (String str:results){
//            BaseBarSeries barSeries = FileStockDailyData.load(str);
//            TrendUpIndicator trendUpIndicator = new TrendUpIndicator(barSeries,31,63,250);
//
//            Rule entryRule = new TrendUpRule(trendUpIndicator);
//
//            int endIndex = barSeries.getEndIndex();
//            for(int i = 0;i<endIndex;i++){
//                System.out.println(entryRule.isSatisfied(i));
//            }
//
//
//
////                    .and(new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63)))
////                    .and(new JTurningPointRule(new JTurningPointIndicator(barSeries,0)));
////            Rule exitRule = new VolumeGreaterThanRule(new VolumeGreaterThanIndicator(barSeries,5,63));
////
////            BaseStrategy baseStrategy = new BaseStrategy(entryRule,exitRule);
////
////            BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
////            TradingRecord tradingRecord = barSeriesManager.run(baseStrategy);
////
////            System.out.println("Number of positions for the strategy: " + tradingRecord.getTrades().size());
//        }
//
////        TrendUpIndicator trendUpIndicator = new TrendUpIndicator(barSeries,31,63,250);
////        System.out.println(trendUpIndicator.getValue(endIndex));
////
////        Rule entryRule = new TrendUpRule(trendUpIndicator)
////                .and(new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63)))
////                .and(new JTurningPointRule(new JTurningPointIndicator(barSeries,0)));
////        Rule exitRule = new VolumeGreaterThanRule(new VolumeGreaterThanIndicator(barSeries,5,63));
////
////        BaseStrategy baseStrategy = new BaseStrategy(entryRule,exitRule);
////
////        BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
////        TradingRecord tradingRecord = barSeriesManager.run(baseStrategy);
////
////        System.out.println("Number of positions for the strategy: " + tradingRecord.getTrades().size());
//
//
////
    }
}
