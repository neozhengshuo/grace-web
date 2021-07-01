package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.AboveUnder;
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
     * 当天量小于短周期和长周期均量
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

    /**
     * 当天量大于短周期和长周期均量
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    public List<String> getHighVolume(int shortMa,int longMa){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isHighVolume(barSeries,shortMa,longMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前量小于指定周期的均量
     * @param ma
     * @return
     */
    public List<String> getLowVolume(int ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isLowVolume(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 判断第一个均量是否在第二均量的知道位置处
     * @param aboveUnder
     * @param ma1
     * @param ma2
     * @return
     */
    public List<String> getMaVolumePosition(AboveUnder aboveUnder, int ma1, int ma2){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isMaVolumePosition(barSeries,aboveUnder,ma1,ma2);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 在指定天数内量小于短周期和长周期均量的
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    public List<String> getLowVolume(int days,int shortMa,int longMa){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isLowVolume(barSeries,days,shortMa,longMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 在指定天数内量小于短周期和长周期均量的
     * @param shortMa 短周期
     * @param longMa 长周期
     * @return
     */
    public List<String> getVolumeAbove(int shortMa,int longMa){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isVolumeAbove(barSeries,shortMa,longMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 判断量在指定的天数内，有多少次大于5日和63日均量（价上涨）。
     * @param inDays 指定的天数内
     * @param times 出现多少次大于5日和63日均量的情况。
     * @return
     */
    public List<String> getVolumeGreater(int inDays,int times){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = VolumeUtils.isVolumeGreater(barSeries,inDays,times);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }
}
