package com.zhs.rule;

import com.zhs.indicator.VolumeGreaterThanIndicator;
import com.zhs.indicator.VolumeLessThanIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class VolumeGreaterThanRule extends AbstractRule {
    private final VolumeGreaterThanIndicator volumegreaterThanIndicator;

    public VolumeGreaterThanRule(VolumeGreaterThanIndicator volumegreaterThanIndicator){
        this.volumegreaterThanIndicator = volumegreaterThanIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = volumegreaterThanIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
