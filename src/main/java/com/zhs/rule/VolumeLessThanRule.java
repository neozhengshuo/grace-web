package com.zhs.rule;

import com.zhs.indicator.VolumeLessThanIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class VolumeLessThanRule extends AbstractRule {

    private final VolumeLessThanIndicator volumeLessThanIndicator;

    public VolumeLessThanRule(VolumeLessThanIndicator volumeLessThanIndicator){
        this.volumeLessThanIndicator = volumeLessThanIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = volumeLessThanIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
