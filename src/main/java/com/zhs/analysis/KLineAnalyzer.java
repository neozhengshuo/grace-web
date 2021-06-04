package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.KLineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class KLineAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(KLineAnalyzer.class);

    public KLineAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 判断在指定的天数内是否出现长K线
     * @param day
     * @param multiple
     * @return
     */
    public List<String> getLongKline(int day, float multiple){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = KLineUtil.isLongKline(barSeries,day,multiple);
            if(hit){
                results.add(file);
            }
        }
        for (String out:KLineUtil.results){
            System.out.println(out);
        }
        KLineUtil.results = new ArrayList<>();
        return results;
    }
}
