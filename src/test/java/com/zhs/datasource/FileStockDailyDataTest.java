package com.zhs.datasource;

import com.zhs.entities.Stock;
import com.zhs.utils.os.OsInfo;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;

public class FileStockDailyDataTest {
    @Test
    public void loadMethodTest(){
        if(OsInfo.isMacOSX()){
            BaseBarSeries barSeries = FileStockDailyData.load("/Users/zhengshuo/IdeaProjects/grace-data/stock-files/SH#600019.txt");
            System.out.println(barSeries.getBarCount());
        }else if(OsInfo.isWindows()){
            //...
        }
    }

    @Test
    public void loadStockTest(){
        Stock stock = FileStockDailyData.loadStock("SH#600019.txt");
        System.out.println(stock.getCode()+ " Count:"+stock.getDayCount());
    }
}
