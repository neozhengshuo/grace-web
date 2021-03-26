package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.entities.dict.StockGroup;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeadStockTest {
    private final List<String> stockDataPaths;

    public LeadStockTest() throws IOException {
        this.stockDataPaths = FileStockDailyData.getStockDataPaths(StockGroup.LEADING);
    }

    @Test
    public void mediumTrendUp() throws IOException{
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(this.stockDataPaths);
        List<String> result = trendAnalyzer.getTrendUp(MovingAverage.MA31,MovingAverage.MA63,10,20);
        result.forEach(System.out::println);
        System.out.println(result.size());
    }
}
