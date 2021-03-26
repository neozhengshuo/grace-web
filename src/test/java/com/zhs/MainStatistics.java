package com.zhs;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Kdj;
import com.zhs.entities.Stock;
import com.zhs.indicator.DIndicator;
import com.zhs.indicator.JIndicator;
import com.zhs.indicator.KIndicator;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.PropertyUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainStatistics {
    private final String[] stockPaths;

    public MainStatistics(){
        this.stockPaths = FileStockDailyData.getDataFiles();
    }

    @Test
    public void kdj_j_statistics(){

            for (int i = 10;i>0;i--){
                int jCount = 0;
                for (String path:this.stockPaths){
                    BaseBarSeries barSeries = FileStockDailyData.load(
                            PropertyUtil.getProperty("stock-daily-data")+"/"+path);
                    AnalysisUtil au = new AnalysisUtil();
                    Stock stock = au.getStock(barSeries);

                    List<Kdj> kdjList = stock.getKdj();
                    int count = kdjList.size();
                    int index = count - i;
                    if(index < 0){
                        continue;
                    }
                    Kdj kdj = kdjList.get(index);
                    if(kdj.getJ()<50){
                        jCount++;
                        System.out.printf("i=%s date=%s jCount=%s%n",i,kdj.getDateTime(),jCount);

                    }
                }
                System.out.printf("######################## i=%s jCount=%s%n",i,jCount);
            }
    }

    @Test
    public void loadAllStock(){
            List<Stock> stockList = new ArrayList<>();
            List<BaseBarSeries> barSeriesList = new ArrayList<>();
            for (String path:this.stockPaths) {
                BaseBarSeries barSeries = FileStockDailyData.load(
                        PropertyUtil.getProperty("stock-daily-data") + "/" + path);
                AnalysisUtil au = new AnalysisUtil();
                Stock stock = au.getStock(barSeries);
                stockList.add(stock);
                System.out.println("loaded:" + stock.getCode());
            }
    }

    @Test
    public void Test() throws ParseException {
        Date date = DateFormat.getDateInstance().parse("2021/02/01");
        System.out.println(date);

    }
}
