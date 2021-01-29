package com.zhs.datasource;

import com.zhs.entities.Stock;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;

import java.util.List;

public class FileStockDailyDataTest {
    @Test
    public void loadMethodTest(){
//        List<?> result1 =  FileStockDailyData.load();
//        System.out.println(result1.size());
//        System.out.println("....");
        BaseBarSeries barSeries = FileStockDailyData.load("SH#600019.txt");
        System.out.println(barSeries.getBarCount());
    }

    @Test
    public void loadStockTest(){
        Stock stock = FileStockDailyData.loadStock("SH#600019.txt");
        System.out.println(stock.getCode()+ " Count:"+stock.getDayCount());
    }
}
