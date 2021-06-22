package com.zhs.indicator;

import com.zhs.entities.dict.GreaterLess;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;


public class VolumeCompareIndicator extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final SMAIndicator shortSmaIndicator ;
    private final SMAIndicator longSmaIndicator;
    private final GreaterLess greaterLess;

    public VolumeCompareIndicator(BarSeries barSeries, GreaterLess greaterLess,int shortMaDays,int longMaDays){
        super(barSeries);
        this.barSeries = barSeries;
        this.greaterLess = greaterLess;
        this.shortSmaIndicator = new SMAIndicator(new VolumeIndicator(barSeries),shortMaDays);
        this.longSmaIndicator = new SMAIndicator(new VolumeIndicator(barSeries),longMaDays);
    }

    @Override
    protected Boolean calculate(int i) {
        int currentVolume = barSeries.getBar(i).getVolume().intValue();
        int shortSmaVolume = shortSmaIndicator.getValue(i).intValue();
        int longSmaVolume = longSmaIndicator.getValue(i).intValue();

        if(greaterLess == GreaterLess.GREATER){
            return currentVolume > shortSmaVolume && currentVolume > longSmaVolume;
        }
        if(greaterLess == GreaterLess.LESS){
            return currentVolume < shortSmaVolume && currentVolume < longSmaVolume;
        }
        return false;
    }
}
