package com.zhs.analysis.shape;

import com.zhs.analysis.ShapeAnalyzer;
import com.zhs.analysis.TrendAnalyzer;
import com.zhs.analysis.VolumeAnalyzer;
import com.zhs.datasource.FileStockDailyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

import java.util.ArrayList;
import java.util.List;

public class VolumeShrinkageShapeAnalyzer {
    private final List<String> fileList;
    private final int shortMa;
    private final int midMa;
    private final int longMa;
    private final int shortVolMa;
    private final int longVolMa;
    private final int k;
    private final int days;
    private static final Logger logger = LoggerFactory.getLogger(VolumeShrinkageShapeAnalyzer.class);

    public VolumeShrinkageShapeAnalyzer(List<String> fileList){
        this.fileList = fileList;
        this.shortMa = 5;
        this.midMa = 31;
        this.longMa = 63;
        this.longVolMa = 63;
        this.shortVolMa = 5;
        this.k = 25;
        this.days = 5;
    }

    private boolean isLowVolumeAndLowK(BarSeries barSeries){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<this.days+1) return false;

        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,this.shortMa);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,this.longMa);
        StochasticOscillatorKIndicator kIndicator = new StochasticOscillatorKIndicator(barSeries,9);
        for (int i = endIndex;i>endIndex-this.days;i--){
            int currentVol = barSeries.getBar(i).getVolume().intValue();
            int shortVol = shortSmaIndicator.getValue(i).intValue();
            int longVol = longSmaIndicator.getValue(i).intValue();
            float k = kIndicator.getValue(i).floatValue();
            if (currentVol < shortVol && currentVol< longVol && k < this.k){
                return true;
            }
        }

        return false;
    }

    public List<String> analyzer(){
        List<String> results = new ArrayList<>();

        for (String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = this.isLowVolumeAndLowK(barSeries);
            if (hit){
                results.add(file);
            }
        }
        return results;
    }
}
