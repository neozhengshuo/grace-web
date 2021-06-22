package com.zhs.rule;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.AboveUnder;
import com.zhs.entities.dict.UpDown;
import com.zhs.indicator.*;
import org.junit.jupiter.api.Test;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.ProfitLossPercentageCriterion;

import java.util.List;

public class TrendRuleTest {

    private BarSeries loadSingle(int index){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();
        return FileStockDailyData.load(results.get(index));
    }

    private TradingRecord runRule(BarSeries barSeries){
        // 入场规则
        //
        Rule entryRule = null;
        entryRule = new TrendRule(new TrendIndicator(barSeries, 31,UpDown.UP));
//        entryRule = entryRule.and(new TrendRule(new TrendIndicator(barSeries,63,UpDown.UP)));
//        entryRule = entryRule.and(new TrendRule(new TrendIndicator(barSeries,250,UpDown.UP)));
//        entryRule = entryRule.and(new MaPositionRule(new MaPositionIndicator(barSeries,AboveUnder.ABOVE,31,63)));
//        entryRule = entryRule.and(new MaPositionRule(new MaPositionIndicator(barSeries,AboveUnder.ABOVE,63,250)));
        entryRule = entryRule.and(new VolumeLessThanRule(new VolumeLessThanIndicator(barSeries,5,63)));
//        entryRule = entryRule.and(new JTurningPointRule(new JTurningPointIndicator(barSeries,UpDown.UP,0)));
        entryRule.and(new KLinePatternRule(new KLinePatternIndicator(barSeries)));

        // 出场规则
        //
        Rule exitRule = new JTurningPointRule(new JTurningPointIndicator(barSeries, UpDown.DOWN,80));

        // 计算交易次数
        //
        BaseStrategy baseStrategy = new BaseStrategy(entryRule,exitRule);
        BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
        return barSeriesManager.run(baseStrategy);
    }

    @Test
    public void singleTest(){
        BarSeries barSeries = loadSingle(123);
        System.out.println(barSeries.getName());

        TradingRecord tradingRecord = runRule(barSeries);
        ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
        float percentage = profitLossPercentageCriterion.calculate(barSeries,tradingRecord).floatValue();
        System.out.println("percentage:"+percentage);
    }

    @Test
    public void allTest(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        float winCount = 0;
        float loseCount = 0;
        float pinCount = 0;
        for (String file:results){
            BarSeries barSeries = FileStockDailyData.load(file);
            TradingRecord tradingRecord = runRule(barSeries);
            ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
            float percentage = profitLossPercentageCriterion.calculate(barSeries,tradingRecord).floatValue();
            System.out.println("percentage:"+percentage);

            if(percentage>0F){
                winCount++;
            }else if (percentage<0F){
                loseCount++;
            }else{
                pinCount++;
            }
        }

        // 计算成功率
        //
        System.out.println("WinCount:"+winCount+" "+"loseCount:"+loseCount+" pinCount:"+pinCount);
        System.out.println(((winCount)/(winCount+pinCount+loseCount))*100+"%");
    }

    @Test
    public void KLinePatternIndicator_Test(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();

        for (String file:results){
            BarSeries barSeries = FileStockDailyData.load(file);
            KLinePatternIndicator kLinePatternIndicator = new KLinePatternIndicator(barSeries);
            boolean hit;
            for (int i = barSeries.getEndIndex();i>0;i--){
                hit = kLinePatternIndicator.getValue(i);
                if(hit){
                    System.out.println(barSeries.getName()+"  "+barSeries.getBar(i).getSimpleDateName());
                    break;
                }
            }
        }
    }
}
