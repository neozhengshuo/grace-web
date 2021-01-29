package com.zhs.indicator;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class JIndicator extends CachedIndicator<Num> {
    private final Indicator<Num> k;
    private final Indicator<Num> d;

    public JIndicator(Indicator<Num> k,Indicator<Num> d) {
        super(k);
        this.k = k;
        this.d = d;
    }

    @Override
    protected Num calculate(int index) {
        Num j1 = this.k.getValue(index).multipliedBy(numOf(3));
        Num j2 = this.d.getValue(index).multipliedBy(numOf(2));
        return j1.minus(j2);
    }
}
