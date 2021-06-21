package com.zhs.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

public class VolumeLessThanIndicator extends CachedIndicator<Boolean> {

    private final BarSeries barSeries;
    private final SMAIndicator shortSmaIndicator ;
    private final SMAIndicator longSmaIndicator;

    public VolumeLessThanIndicator(BarSeries barSeries, int shortDays, int longDays){
        super(barSeries);
        this.barSeries = barSeries;
        VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
        this.shortSmaIndicator = new SMAIndicator(volumeIndicator, shortDays);
        this.longSmaIndicator = new SMAIndicator(volumeIndicator, longDays);
    }

    @Override
    protected Boolean calculate(int i) {

        int currentVolume = barSeries.getBar(i).getVolume().intValue();
        int shortSmaVolume = shortSmaIndicator.getValue(i).intValue();
        int longSmaVolume = longSmaIndicator.getValue(i).intValue();

        return currentVolume < shortSmaVolume;
    }
}
