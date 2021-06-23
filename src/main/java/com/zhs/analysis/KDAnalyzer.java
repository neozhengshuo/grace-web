package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.indicator.RsvIndicator;
import com.zhs.utils.KdjUtil;
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
            boolean hit = KdjUtil.isKLow(barSeries,k);
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
            boolean hit = KdjUtil.isKLow(barSeries,days,k);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getKdjLow(float k,float d,float j){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
            boolean hit = KdjUtil.isKdjLow(rsvIndicator,k,d,j);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * J值是否在指定的值以下开始向上
     * @param j
     * @return
     */
    public List<String> getJUp(float j){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
            boolean hit = KdjUtil.isJUp(rsvIndicator,j);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getJUpWithVolume(float j){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            RsvIndicator rsvIndicator = new RsvIndicator(barSeries,9);
            boolean hit = KdjUtil.isJUpWithVolume(rsvIndicator,j,5,63);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }




}
