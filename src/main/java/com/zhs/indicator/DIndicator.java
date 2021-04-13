package com.zhs.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class DIndicator extends CachedIndicator<Num> {
    private Indicator<Num> indicator;

    public DIndicator(KIndicator k) {
        super(k);
        indicator = k;
    }

    @Override
    protected Num calculate(int index) {
        Num a = this.numOf(2/3);
        Num b = this.numOf(1/3);
        if(index == 0 ) return numOf(50);
        Num preD = getValue(index-1);
        Num d1 = a.multipliedBy(preD);
        Num d2 = b.multipliedBy(this.indicator.getValue(index));
        Num d = d1.minus(d2);
        return d;

//        if(index == 0) return numOf(50);
//        Num preD = getValue(index - 1);
//        Num d1 = numOf(1).multipliedBy(this.indicator.getValue(index));
//        Num d2 = numOf(2).multipliedBy(preD);
//        Num d1_d2 = d1.plus(d2);
//        return d1_d2.dividedBy(numOf(3));
    }
}
