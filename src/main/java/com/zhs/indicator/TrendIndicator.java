package com.zhs.indicator;

import com.zhs.entities.dict.UpDown;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.io.Closeable;

public class TrendIndicator extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final SMAIndicator smaIndicator;
    private final UpDown upDown;

    public TrendIndicator(BarSeries barSeries, int trendDays, UpDown upDown){
        super(barSeries);
        this.barSeries = barSeries;
        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        this.smaIndicator = new SMAIndicator(closePriceIndicator,trendDays);
        this.upDown = upDown;
    }


    @Override
    protected Boolean calculate(int i) {
        int endIndex = barSeries.getEndIndex();
        if(endIndex<1) return false;

        float current_sma_value = smaIndicator.getValue(i).floatValue();
        float before1_sma_value = smaIndicator.getValue(i-1).floatValue();

        if(upDown == UpDown.UP){
            return current_sma_value>=before1_sma_value;
        }
        if(upDown == UpDown.DOWN){
            return current_sma_value<before1_sma_value;
        }
        return false;
    }
}
