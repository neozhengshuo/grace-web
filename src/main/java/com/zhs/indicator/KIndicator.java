package com.zhs.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.num.Num;

import java.math.BigDecimal;

public class KIndicator extends CachedIndicator<Num> {

    private Indicator<Num> indicator;

    public KIndicator(StochasticOscillatorKIndicator rsv) {
        super(rsv);
        this.indicator = rsv;
    }

    @Override
    protected Num calculate(int index) {
        Num a = this.numOf(2F/3F);
        Num b = this.numOf(1F/3F);
        if(index == 0 ) return numOf(50);
        Num k1 = a.multipliedBy(getValue(index-1));
        Num k2 = b.multipliedBy(this.indicator.getValue(index));
        Num k = k1.minus(k2);
        return k;
    }
}
