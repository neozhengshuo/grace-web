package com.zhs.strategies.trend;

import com.zhs.analysis.*;
import com.zhs.analysis.shape.VolumeShrinkageShapeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Kdj;
import com.zhs.entities.dict.RedGreen;
import com.zhs.indicator.DIndicator;
import com.zhs.indicator.KIndicator;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

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
        int days = 4;
        float abovePricePercentage = 1.5F;
        float underPricePercentage = 0.3F;
        ShapeAnalyzer shapeAnalyzer = new ShapeAnalyzer(results,days,abovePricePercentage,underPricePercentage);
        results = shapeAnalyzer.analyzer();

        String strOut = "test";
        FileUtil.writeTxtFile(strOut, results, true);
    }

}
