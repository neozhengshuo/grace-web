package com.zhs.utils;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Kdj;
import com.zhs.entities.MAs;
import com.zhs.entities.Stock;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;

import java.util.List;

public class AnalysisUtilTest {
    @Test
    void calculationKdjTest(){
        BaseBarSeries barSeries = FileStockDailyData.load("SH#600019.txt");
        AnalysisUtil analysisUtil = new AnalysisUtil();
        List<Kdj> kdjList = analysisUtil.calculationKdj(barSeries);
        for(Kdj kdj:kdjList ){
            System.out.println(kdj);
        }
    }

    @Test
    void calculationMAsTest(){
        BaseBarSeries barSeries = FileStockDailyData.load("SH#600019.txt");
        AnalysisUtil analysisUtil = new AnalysisUtil();
        List<MAs> masList = analysisUtil.calculationMAs(barSeries);
        for(MAs mas:masList){
            System.out.println(mas);
        }
    }

    @Test
    void isTrendUpwardsWithMa31Test(){
        AnalysisUtil analysisUtil = new AnalysisUtil();
        BaseBarSeries barSeries = FileStockDailyData.load("SH#600019.txt");
        Stock stock = analysisUtil.getStock(barSeries);
        boolean isUp = analysisUtil.isTrendUpwardsWithMa31(stock,31);
        System.out.println(isUp);
    }
}
