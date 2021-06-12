package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.RedGreen;
import com.zhs.utils.VolumeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class VolumeAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(VolumeAnalyzer.class);

    public VolumeAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    public List<String> getExpandVolume(int day, float multiple){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isExpandVolume(barSeries,day,multiple);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getExpandVolume2(RedGreen redGreen,int days){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isExpandVolume2(barSeries,redGreen,days);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前量小于短周期和长周期均量
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    public List<String> getLowVolume(int shortMa,int longMa){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isLowVolume(barSeries,shortMa,longMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }
}
