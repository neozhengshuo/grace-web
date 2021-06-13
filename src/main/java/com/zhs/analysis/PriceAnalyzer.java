package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.KLineUtil;
import com.zhs.utils.PriceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class PriceAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(PriceAnalyzer.class);

    public PriceAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 当前价格在指定天数之前的价格（扣抵判断）
     * @param day
     * @return
     */
    public List<String> getPositionAbove(int day){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPositionAbove(barSeries,day);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价格在指定天数之前的价格（扣抵判断）
     * @param day
     * @return
     */
    public List<String> getPositionUnder(int day){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPositionUnder(barSeries,day);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }


}
