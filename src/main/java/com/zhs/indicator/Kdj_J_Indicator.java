package com.zhs.indicator;

import com.zhs.entities.dict.GreaterLess;
import com.zhs.entities.dict.UpDown;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

public class Kdj_J_Indicator extends CachedIndicator<Boolean> {
    private final JIndicator jIndicator;
    private final float j;
    private final GreaterLess greaterLess;

    public Kdj_J_Indicator(BarSeries barSeries, GreaterLess greaterLess,float j ){
        super(barSeries);
        this.jIndicator = new JIndicator(new StochasticOscillatorKIndicator(barSeries,9));
        this.j = j;
        this.greaterLess = greaterLess;
    }

    @Override
    protected Boolean calculate(int i) {
        int endIndex = this.getBarSeries().getEndIndex();
        if(endIndex < 0) return false;

        float current_j = jIndicator.getValue(i).floatValue();
        if(greaterLess == GreaterLess.GREATER){
            return current_j>=j;
        }
        if(greaterLess == GreaterLess.LESS){
            return current_j<=j;
        }
        return false;
    }
}
