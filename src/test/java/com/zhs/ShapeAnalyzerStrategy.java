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


        /**
         * 确定标的后，在30分钟上按平台交易操作。
         */
        int days = 5;
        float abovePricePercentage = 1.2F;
        float underPricePercentage = 0.7F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();

//        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results);
//        results = shapeAnalyzer.analyzer();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }
}
