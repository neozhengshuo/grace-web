package com.zhs.rule;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.UpDown;
import com.zhs.indicator.TrendIndicator;
import org.junit.jupiter.api.Test;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import java.util.List;

public class TrendRuleTest {

    private BarSeries load(){
        List<String> results = null;
        results = FileStockDailyData.getStockFilesWithFullPath();
        return FileStockDailyData.load(results.get(0));
    }

    @Test
    public void test(){
        BarSeries barSeries = load();
        Rule rule = new TrendRule(new TrendIndicator(barSeries, 250,UpDown.UP))
                .and(new TrendRule(new TrendIndicator(barSeries,63,UpDown.UP)))
                .and(new TrendRule(new TrendIndicator(barSeries,31,UpDown.UP)));

        int endIndex = barSeries.getEndIndex();
        for (int i = 0;i<=endIndex;i++){
            System.out.println(rule.isSatisfied(i)+" "+barSeries.getBar(i).getSimpleDateName());
        }
    }
}
