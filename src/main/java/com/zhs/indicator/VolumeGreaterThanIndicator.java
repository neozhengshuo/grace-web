package com.zhs.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

public class VolumeGreaterThanIndicator  extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final int shortAverageVolume;
    private final int longAverageVolume;

    public VolumeGreaterThanIndicator(BarSeries barSeries, int shortAverageVolume, int longAverageVolume){
        super(barSeries);
        this.barSeries = barSeries;
        this.shortAverageVolume = shortAverageVolume;
        this.longAverageVolume = longAverageVolume;
    }

    @Override
    protected Boolean calculate(int i) {
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        SMAIndicator shortSmaIndicator = new SMAIndicator(volumeIndicator,shortAverageVolume);
        SMAIndicator longSmaIndicator = new SMAIndicator(volumeIndicator,longAverageVolume);

        int currentVolume = volumeIndicator.getValue(i).intValue();
        int shortSmaVolume = shortSmaIndicator.getValue(i).intValue();
        int longSmaVolume = longSmaIndicator.getValue(i).intValue();


        return currentVolume >= shortSmaVolume && currentVolume >= longSmaVolume;
    }
}
