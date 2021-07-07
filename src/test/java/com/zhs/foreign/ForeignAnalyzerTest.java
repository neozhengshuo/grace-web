package com.zhs.foreign;

import com.zhs.analysis.ForeignAnalyzer;
import com.zhs.datasource.ForeignDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ForeignAnalyzerTest {
    @Test
    public void test() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        results = foreignAnalyzer.getIncrementUp(0.7F,1);

        for (String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }

    @Test
    public void testTrendUp() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        results = foreignAnalyzer.getTrendUp(5);

        foreignAnalyzer = new ForeignAnalyzer(results);
        results = foreignAnalyzer.getIncrementUp(0.15F,0);

        for (String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }

    @Test
    public void testIncrementShrink() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        float allCount = results.size();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        float incrementCount =  foreignAnalyzer.getCurrentIncrementUp().size();

        foreignAnalyzer = new ForeignAnalyzer(results);
        float shrinkCount =  foreignAnalyzer.getCurrentShrink().size();

        System.out.printf("AllCount,Increment,Shrink,IncrementPercentage%n%s,%s,%s,%s%n",allCount,incrementCount,shrinkCount,incrementCount/allCount);
    }

    @Test
    public void testGetComeIn() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        float allCount = results.size();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        results =  foreignAnalyzer.getComeIn(7);

        for (String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }

    @Test
    public void getIncrementUp() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        float allCount = results.size();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        results =  foreignAnalyzer.getContinuousIncrementUp(6);

        for (String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }
}
