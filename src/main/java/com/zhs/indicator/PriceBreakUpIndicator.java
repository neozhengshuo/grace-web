package com.zhs.indicator;

import com.zhs.entities.dict.AboveUnder;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 * 价格突破指标
 * 用于判断价格是否突破指定的均线
 */
public class PriceBreakUpIndicator  extends CachedIndicator<Boolean> {
    private final BarSeries barSeries;
    private final SMAIndicator shortSmaIndicator;
    private final SMAIndicator midSmaIndicator;
    private final SMAIndicator longSmaIndicator;

    public PriceBreakUpIndicator(BarSeries barSeries, int shortMa, int midMa,int longMa){
        super(barSeries);
        this.barSeries = barSeries;
        this.shortSmaIndicator = new SMAIndicator(new ClosePriceIndicator(barSeries),shortMa);
        this.midSmaIndicator = new SMAIndicator(new ClosePriceIndicator(barSeries),midMa);
        this.longSmaIndicator = new SMAIndicator(new ClosePriceIndicator(barSeries),longMa);
    }

    @Override
    protected Boolean calculate(int i) {
//        float shortSmaValue = shortSmaIndicator.getValue(i).floatValue();
//        float midSmaValue = midSmaIndicator.getValue(i).floatValue();
//        float longSmaValue = longSmaIndicator.getValue(i).floatValue();

        float shortSmaValue = new SMAIndicator(new ClosePriceIndicator(barSeries),5).getValue(i).floatValue();
        float midSmaValue = new SMAIndicator(new ClosePriceIndicator(barSeries),31).getValue(i).floatValue();
        float longSmaValue = new SMAIndicator(new ClosePriceIndicator(barSeries),63).getValue(i).floatValue();

        Bar bar = barSeries.getBar(i);
        float curOpen = bar.getOpenPrice().floatValue();
        float curClose = bar.getClosePrice().floatValue();

        boolean hit1 = curClose>curOpen;
        boolean hit2 = curClose>shortSmaValue && curOpen < shortSmaValue;
        boolean hit3 = curClose>midSmaValue && curOpen < midSmaValue;
        boolean hit4 = curClose>longSmaValue && curOpen < longSmaValue;

        boolean a = hit1 && hit2 && hit3 && hit4;

        return  a;
    }
}
