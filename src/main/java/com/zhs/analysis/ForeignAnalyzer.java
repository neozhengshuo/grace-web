package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.datasource.ForeignDataSource;
import com.zhs.utils.ForeignUtil;
import com.zhs.utils.KdjUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForeignAnalyzer {

    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(ForeignAnalyzer.class);

    public ForeignAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    public List<String> getIncrementUp(float rate,int days) throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isIncrementUp(barSeries,rate,days);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getContinuousIncrementUp(int days) throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isContinuousIncrementUp(barSeries,days);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getTrendUp(int ma) throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isTrendUp(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getCurrentIncrementUp() throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isCurrentIncrementUp(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getCurrentShrink() throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isCurrentShrink(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getComeIn(int days) throws IOException, ParseException {
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = ForeignUtil.isComeIn(barSeries,days);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }
}
