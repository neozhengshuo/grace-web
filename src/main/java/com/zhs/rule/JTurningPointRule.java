package com.zhs.rule;

import com.zhs.indicator.JTurningPointIndicator;
import com.zhs.indicator.TrendUpIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class JTurningPointRule extends AbstractRule {

    private final JTurningPointIndicator jTurningPointIndicator;

    public JTurningPointRule(JTurningPointIndicator jTurningPointIndicator){
        this.jTurningPointIndicator = jTurningPointIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        final boolean satisfied = jTurningPointIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
