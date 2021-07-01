package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.BollUtil;
import com.zhs.utils.KdjUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class BollAnalyzer {

    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(KDAnalyzer.class);

    public BollAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }


    public List<String> getShrink( float rate){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = BollUtil.isShrink(barSeries,rate);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }
}
