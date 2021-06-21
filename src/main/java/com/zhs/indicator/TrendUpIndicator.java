package com.zhs.indicator;

import com.zhs.entities.dict.UpDown;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

public class TrendUpIndicator extends CachedIndicator<Boolean> {

    private final BarSeries barSeries;
    private final int shortTrendDays;
    private final int midTrendDays;
    private final int longTrendDays;

    public TrendUpIndicator(BarSeries barSeries, int shortTrendDays,int midTrendDays,int longTrendDays){
        super(barSeries);
        this.barSeries = barSeries;
        this.shortTrendDays = shortTrendDays;
        this.midTrendDays = midTrendDays;
        this.longTrendDays = longTrendDays;
    }

    @Override
    protected Boolean calculate(int i) {
        int endIndex = barSeries.getEndIndex();
        if(endIndex<0) return false;

        ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(barSeries);
        SMAIndicator shortSmaIndicator =new SMAIndicator(closePriceIndicator,shortTrendDays);
        SMAIndicator midSmaIndicator =new SMAIndicator(closePriceIndicator,midTrendDays);
        SMAIndicator longSmaIndicator =new SMAIndicator(closePriceIndicator,longTrendDays);

        boolean shortTrendHit = shortSmaIndicator.getValue(i).floatValue()>=shortSmaIndicator.getValue(i-1).floatValue();
        boolean midTrendHit = midSmaIndicator.getValue(i).floatValue()>=midSmaIndicator.getValue(i-1).floatValue();
        boolean longTrendHit = longSmaIndicator.getValue(i).floatValue()>=longSmaIndicator.getValue(i-1).floatValue();
        boolean trendHit = shortTrendHit && midTrendHit && longTrendHit;

        boolean posHit1 = shortSmaIndicator.getValue(i).floatValue()>midSmaIndicator.getValue(i).floatValue();
        boolean posHit2 = midSmaIndicator.getValue(i).floatValue()>longSmaIndicator.getValue(i).floatValue();
        boolean posHit = posHit1 && posHit2;

////        boolean posHit1 = shortSmaIndicator.getValue(i).floatValue()>midSmaIndicator.getValue(i).floatValue();
//        boolean posHit2 = midSmaIndicator.getValue(i).floatValue()>longSmaIndicator.getValue(i).floatValue();
//        boolean posHit = posHit2;

        return trendHit && posHit;
    }
}
