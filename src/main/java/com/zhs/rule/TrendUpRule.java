package com.zhs.rule;

import com.zhs.indicator.JTurningPointIndicator;
import com.zhs.indicator.KLinePatternIndicator;
import com.zhs.indicator.TrendUpIndicator;
import com.zhs.indicator.VolumeLessThanIndicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AbstractRule;

public class TrendUpRule extends AbstractRule {

    private final TrendUpIndicator trendUpIndicator;
    private final VolumeLessThanIndicator volumeLessThanIndicator;
    private final JTurningPointIndicator jTurningPointIndicator;
    private final KLinePatternIndicator kLinePatternIndicator;

    public TrendUpRule(TrendUpIndicator trendUpIndicator,
                       VolumeLessThanIndicator volumeLessThanIndicator,
                       JTurningPointIndicator jTurningPointIndicator,
                       KLinePatternIndicator kLinePatternIndicator){
        this.trendUpIndicator = trendUpIndicator;
        this.volumeLessThanIndicator = volumeLessThanIndicator;
        this.jTurningPointIndicator = jTurningPointIndicator;
        this.kLinePatternIndicator = kLinePatternIndicator;
    }

    @Override
    public boolean isSatisfied(int i, TradingRecord tradingRecord) {
        boolean hit1 = trendUpIndicator.getValue(i);
        boolean hit2 = volumeLessThanIndicator.getValue(i);
        boolean hit3 = jTurningPointIndicator.getValue(i);
//        boolean hit4 = kLinePatternIndicator.getValue(i);

        final boolean satisfied = hit1 && hit2 && hit3 ;// && hit4;
        traceIsSatisfied(i,satisfied);
        return satisfied;
    }
}
