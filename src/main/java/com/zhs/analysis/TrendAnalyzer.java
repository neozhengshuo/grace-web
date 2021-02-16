package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * 趋势分析，以趋势向上为基础配合其他指标（boll,kdj等）选出需要关注的股票。
 */
public class TrendAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(TrendAnalyzer.class);
    private static final AnalysisUtil analysisUtil = new AnalysisUtil();
    private List<String> trendUpList;

    /**
     * 实例化一个TrendAnalyzer类的新对象
     */
    public TrendAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 获取趋势向上的股票
     */
    public List<String> loadTrendUp(){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean isHit1 = analysisUtil.isTrendUpwards(baseBarSeries,31,10);
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,63,20);
            boolean isHit3 = analysisUtil.isTrendUpwards(baseBarSeries,250,50);
            if(isHit1 && isHit2 && isHit3){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取最低价在中轨以下的股票(以趋势向上为基础)
     * @return 结果
     */
    public List<String> getBollMid(){
        List<String> results = new ArrayList<>();
        if(this.trendUpList == null || this.trendUpList.size()<=0){
            this.trendUpList = this.loadTrendUp();
        }
        for(String file:this.trendUpList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.isBollMid(baseBarSeries);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取当天J值涨，但前两天J值下跌的股票。
     * @return 结果
     */
    public List<String> getKDJ(){
        List<String> results = new ArrayList<>();
        if(this.trendUpList == null || this.trendUpList.size()<=0){
            this.trendUpList = this.loadTrendUp();
        }
        for (String file:this.trendUpList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.isKdjUp(baseBarSeries);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }
}
