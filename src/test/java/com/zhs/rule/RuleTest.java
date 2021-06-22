package com.zhs.rule;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.UpDown;
import com.zhs.indicator.*;
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

        float winCount = 0;
        float loseCount = 0;
        float pinCount = 0;
        for (String file:results){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            Rule entryRule = new TrendUpRule(
                    new TrendUpIndicator(barSeries,31,63,250),
                    new VolumeLessThanIndicator(barSeries,5,63),
                    new JTurningPointIndicator(barSeries,UpDown.UP,0),
                    new KLinePatternIndicator(barSeries));
//            Rule exitRule = new VolumeGreaterThanRule(new VolumeGreaterThanIndicator(barSeries,5,63));
            Rule exitRule = new JTurningPointRule(new JTurningPointIndicator(barSeries,UpDown.DOWN,80));

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
    }
}
