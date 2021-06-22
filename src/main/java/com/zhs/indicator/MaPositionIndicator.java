package com.zhs.indicator;

import com.zhs.entities.dict.AboveUnder;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class MaPositionIndicator extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final AboveUnder aboveUnder;
    private final SMAIndicator firstSmaIndicator;
    private final SMAIndicator secondSmaIndicator;

    public MaPositionIndicator(BarSeries barSeries, AboveUnder aboveUnder, int firstMa, int secondMa){
        super(barSeries);
        this.barSeries = barSeries;
        this.aboveUnder = aboveUnder;
        this.firstSmaIndicator = new SMAIndicator(new ClosePriceIndicator(barSeries),firstMa);
        this.secondSmaIndicator = new SMAIndicator(new ClosePriceIndicator(barSeries),secondMa);
    }

    @Override
    protected Boolean calculate(int i) {
        float firstMaValue = firstSmaIndicator.getValue(i).floatValue();
        float secondMaValue = secondSmaIndicator.getValue(i).floatValue();
        if(aboveUnder == AboveUnder.ABOVE){
            return firstMaValue>secondMaValue;
        }
        if(aboveUnder == AboveUnder.UNDER){
            return firstMaValue<secondMaValue;
        }
        return false;
    }
}
