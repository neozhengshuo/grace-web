package com.zhs.rule;

import com.zhs.indicator.VolumeCompareIndicator;
import com.zhs.indicator.VolumeGreaterThanIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class VolumeCompareRule extends AbstractRule {
    private final VolumeCompareIndicator volumeGreaterThanIndicator;

    public VolumeCompareRule(VolumeCompareIndicator volumeCompareIndicator){
        this.volumeGreaterThanIndicator = volumeCompareIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = volumeGreaterThanIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
