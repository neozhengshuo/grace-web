package com.zhs.indicator;

import com.zhs.entities.dict.AboveUnder;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;

public class KLinePatternIndicator  extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final int endIndex;

    public KLinePatternIndicator(BarSeries barSeries){
        super(barSeries);
        this.barSeries = barSeries;
        this.endIndex = barSeries.getEndIndex();
    }

    @Override
    protected Boolean calculate(int i) {
        if(i<=3) return false;

        Bar currentBar = barSeries.getBar(i);
        Bar before1Bar = barSeries.getBar(i-1);
//        Bar before2Bar = barSeries.getBar(i-1-1);

        // 计算前1天的K线是否有上影线
        //
        float before1Open = before1Bar.getOpenPrice().floatValue();
        float before1Close = before1Bar.getClosePrice().floatValue();
        float before1High = before1Bar.getHighPrice().floatValue();
        float before1Low = before1Bar.getLowPrice().floatValue();
        float before1RealBody = Math.abs(before1Open-before1Close);
        float before1UpperShadow = before1High-Math.max(before1Open, before1Close);
        boolean hit1 =  before1UpperShadow>before1RealBody;

        // 计算当天的收盘价大于前一天的收盘价；且最低价大于前一天的最低价
        float currentClose = currentBar.getClosePrice().floatValue();
        float currentLow = currentBar.getLowPrice().floatValue();
        boolean hit2 = currentClose>=Math.max(before1Open,before1Close);
        boolean hit3 = currentLow>=before1Low;

        return hit1 && hit2 && hit3;
    }
}
