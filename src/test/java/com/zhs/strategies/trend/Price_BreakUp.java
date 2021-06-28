package com.zhs.strategies.trend;

import com.zhs.analysis.PriceAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.GreaterLess;
import com.zhs.entities.dict.UpDown;
import com.zhs.indicator.*;
import com.zhs.rule.*;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.ProfitLossPercentageCriterion;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Price_BreakUp {

    @Test
    public void breakUpTest(){
        TrendAnalyzer trendAnalyzer;
        PriceAnalyzer priceAnalyzer;
        VolumeAnalyzer volumeAnalyzer;
        int days = 6;

        List<String> results;
        results =  FileStockDailyData.getStockFilesWithFullPath();

        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(31,250);
        trendAnalyzer = new TrendAnalyzer(results);
        results = trendAnalyzer.getMaPositionAbove(63,250);

        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(5,days);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(31,days);
        priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.getPriceBreakUp(63,days);
        priceAnalyzer = new PriceAnalyzer(results);

        volumeAnalyzer = new VolumeAnalyzer(results);
        results = volumeAnalyzer.getHighVolume(5,63);

        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }



    private TradingRecord runRule(BarSeries barSeries) {
        Rule entryRule = new TrueFalseRule(new PriceBreakUpIndicator(barSeries,5,31,63));//.and(new TrueFalseRule(new VolumeCompareIndicator(barSeries,GreaterLess.GREATER,5,63)));
        entryRule = entryRule.and(new TrueFalseRule(new Kdj_J_Indicator(barSeries,GreaterLess.LESS,50)));
//        entryRule = entryRule.and(new TrueFalseRule(new VolumeCompareIndicator(barSeries,GreaterLess.GREATER,5,63)));
        Rule exitRule = new TrueFalseRule(new Kdj_J_Indicator(barSeries,GreaterLess.GREATER,90));

        // 计算交易次数
        //
        BaseStrategy baseStrategy = new BaseStrategy(entryRule, exitRule);
        BarSeriesManager barSeriesManager = new BarSeriesManager(barSeries);
        return barSeriesManager.run(baseStrategy);
    }

    @Test
    public void ruleSingleTest(){
        List<String> results =  FileStockDailyData.getStockFilesWithFullPath();
        BarSeries barSeries = FileStockDailyData.load(results.get(524));
        System.out.println(barSeries.getName());

        TradingRecord tradingRecord = this.runRule(barSeries);
        ProfitLossPercentageCriterion profitLossPercentageCriterion = new ProfitLossPercentageCriterion();
        float percentage = profitLossPercentageCriterion.calculate(barSeries,tradingRecord).floatValue();
        System.out.println("percentage:"+percentage+" tradingCount:"+tradingRecord.getTradeCount());

        List<Trade> tradeList = tradingRecord.getTrades();
        for (Trade trade :tradeList){
//            if(trade.getProfit().floatValue()<0F)
                System.out.println(barSeries.getBar(trade.getEntry().getIndex()).getSimpleDateName() + "  " +barSeries.getBar(trade.getExit().getIndex()).getSimpleDateName());
        }
    }

    @Test
    public void runAllRule(){
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
//        System.out.println(((winCount+pinCount)/(winCount+loseCount)*100+"%"));
        System.out.println(((winCount)/(winCount+loseCount+pinCount)*100+"%"));
    }

    @Test
    public void temp(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();
//
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(results);
        results = priceAnalyzer.isPriceIncreased(10,0.03F,5,63);
//        Hashtable<String,Float> dd = new Hashtable<>();
//        for (String str:results){
//            dd.put(str,)
//        }
//
//        for (String str:results){
//            BarSeries barSeries = FileStockDailyData.load(str);
//            float closePrice = barSeries.getLastBar().getClosePrice().floatValue();
//        }


        String strOut = "temp."+Thread.currentThread().getStackTrace()[1].getMethodName();
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
