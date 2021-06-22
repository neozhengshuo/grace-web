package com.zhs.rule;

import com.zhs.indicator.KLinePatternIndicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class KLinePatternRule extends AbstractRule {
    private final KLinePatternIndicator kLinePatternIndicator;

    public KLinePatternRule(KLinePatternIndicator kLinePatternIndicator){
        this.kLinePatternIndicator = kLinePatternIndicator;
    }

    public boolean isSatisfied(int i, TradingRecord tradingRecord){
        final boolean satisfied = kLinePatternIndicator.getValue(i);
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
