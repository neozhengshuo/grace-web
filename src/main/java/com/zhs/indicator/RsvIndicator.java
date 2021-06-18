package com.zhs.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.*;
import org.ta4j.core.num.Num;

public class RsvIndicator extends CachedIndicator<Num> {
    private final Indicator<Num> indicator;

    private final int barCount;

    private HighPriceIndicator highPriceIndicator;

    private LowPriceIndicator lowPriceIndicator;

    public RsvIndicator(BarSeries barSeries,int barCount){
        this(new ClosePriceIndicator(barSeries),barCount,new HighPriceIndicator(barSeries),new LowPriceIndicator(barSeries));
    }

    public RsvIndicator(Indicator<Num> indicator, int barCount, HighPriceIndicator highPriceIndicator, LowPriceIndicator lowPriceIndicator){
        super(indicator);
        this.indicator = indicator;
        this.barCount = barCount;
        this.highPriceIndicator = highPriceIndicator;
        this.lowPriceIndicator = lowPriceIndicator;
    }

    @Override
    protected Num calculate(int index) {
        HighestValueIndicator highestHigh = new HighestValueIndicator(highPriceIndicator, barCount);
        LowestValueIndicator lowestMin = new LowestValueIndicator(lowPriceIndicator, barCount);

        Num highestHighPrice = highestHigh.getValue(index);
        Num lowestLowPrice = lowestMin.getValue(index);

        return indicator.getValue(index).minus(lowestLowPrice).dividedBy(highestHighPrice.minus(lowestLowPrice))
                .multipliedBy(numOf(100));
    }
}
