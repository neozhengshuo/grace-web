package com.zhs.rule;

import com.zhs.indicator.TrendIndicator;
import com.zhs.indicator.TrendUpIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class TrendRule extends AbstractRule {

    private final TrendIndicator trendIndicator;

    public TrendRule(TrendIndicator trendIndicator){
        this.trendIndicator = trendIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = trendIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
