package com.zhs.rule;

import com.zhs.indicator.TrendUpIndicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class TrendUpRule extends AbstractRule {

    private final TrendUpIndicator trendUpIndicator;

    public TrendUpRule(TrendUpIndicator trendUpIndicator){
        this.trendUpIndicator = trendUpIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = trendUpIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
