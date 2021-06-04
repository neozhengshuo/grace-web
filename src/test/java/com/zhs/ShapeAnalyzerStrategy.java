package com.zhs;

import com.zhs.analysis.ShapeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShapeAnalyzerStrategy {
    @Test
    public void test(){
        List<String> results = new ArrayList<>();
        results = FileStockDailyData.getStockFilesWithFullPath();

        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results);
        results = shapeAnalyzer.analyzer();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

}
