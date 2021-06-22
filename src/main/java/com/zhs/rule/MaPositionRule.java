package com.zhs.rule;

import com.zhs.indicator.MaPositionIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class MaPositionRule extends AbstractRule {
    private final MaPositionIndicator maPositionIndicator;

    public MaPositionRule(MaPositionIndicator maPositionIndicator){
        this.maPositionIndicator = maPositionIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = maPositionIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
