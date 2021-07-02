package com.zhs.foreign;

import com.zhs.analysis.ForeignAnalyzer;
import com.zhs.datasource.ForeignDataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForeignAnalyzerTest {
    @Test
    public void test() throws IOException, ParseException {
        List<String> results;
        results = ForeignDataSource.getForeignCsvFileList();

        ForeignAnalyzer foreignAnalyzer = new ForeignAnalyzer(results);
        results = foreignAnalyzer.getIncrementUp(0.1F);

        for (String str:results){
            System.out.println(str);
        }
        System.out.println(results.size());
    }
}
