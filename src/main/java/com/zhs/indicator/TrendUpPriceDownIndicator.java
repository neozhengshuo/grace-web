package com.zhs.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

public class TrendUpPriceDownIndicator extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final SMAIndicator shortSmaIndicator;
    private final SMAIndicator midSmaIndicator;
    private final SMAIndicator longSmaIndicator;
    private final SMAIndicator volume_shortSmaIndicator ;
    private final SMAIndicator volume_longSmaIndicator;

    public TrendUpPriceDownIndicator(BarSeries barSeries){
        super(barSeries);
        this.barSeries = barSeries;

        this.shortSmaIndicator =new SMAIndicator(new ClosePriceIndicator(barSeries),31);
        this.midSmaIndicator =new SMAIndicator(new ClosePriceIndicator(barSeries),63);
        this.longSmaIndicator =new SMAIndicator(new ClosePriceIndicator(barSeries),250);

        this.volume_shortSmaIndicator = new SMAIndicator(new VolumeIndicator(barSeries),5);
        this.volume_longSmaIndicator = new SMAIndicator(new VolumeIndicator(barSeries),63);
    }

    public boolean calculateTrendUp(int i){
        boolean shortTrendHit = shortSmaIndicator.getValue(i).floatValue()>=shortSmaIndicator.getValue(i-1).floatValue();
        boolean midTrendHit = midSmaIndicator.getValue(i).floatValue()>=midSmaIndicator.getValue(i-1).floatValue();
        boolean longTrendHit = longSmaIndicator.getValue(i).floatValue()>=longSmaIndicator.getValue(i-1).floatValue();
        boolean trendHit = shortTrendHit && midTrendHit && longTrendHit;

        boolean posHit1 = shortSmaIndicator.getValue(i).floatValue()>midSmaIndicator.getValue(i).floatValue();
        boolean posHit2 = midSmaIndicator.getValue(i).floatValue()>longSmaIndicator.getValue(i).floatValue();
        boolean posHit = posHit1 && posHit2;

        return trendHit && posHit;
    }

    public boolean calculateVolume(int i){
        int currentVolume = barSeries.getBar(i).getVolume().intValue();
        int shortSmaVolume = shortSmaIndicator.getValue(i).intValue();
        int longSmaVolume = longSmaIndicator.getValue(i).intValue();

        return currentVolume < shortSmaVolume && currentVolume < longSmaVolume;
    }

    @Override
    protected Boolean calculate(int i) {
        boolean hit1 = calculateTrendUp(i);
        boolean hit2 = calculateVolume(i);
        return hit1 && hit2;
    }
}
