package com.zhs.rule;

import com.zhs.indicator.TrendIndicator;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class TrueFalseRule extends AbstractRule {
    private final Indicator<Boolean> indicator;

    public TrueFalseRule(Indicator<Boolean> indicator){
        this.indicator = indicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = indicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
