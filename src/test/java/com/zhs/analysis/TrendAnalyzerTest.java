package com.zhs.analysis;

import org.junit.jupiter.api.Test;

public class TrendAnalyzerTest {
    @Test
    public void trendUpTest(){
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer();
        trendAnalyzer.loadTrendUp(true);
    }
}
