package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.KDUtil;
import com.zhs.utils.VolumeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class KDAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(KDAnalyzer.class);

    public KDAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    public List<String> getKLow(float k){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = KDUtil.isKLow(barSeries,k);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getKLow(int days,float k){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = KDUtil.isKLow(barSeries,days,k);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }




}
