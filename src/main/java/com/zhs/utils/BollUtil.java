package com.zhs.utils;

import com.zhs.datasource.FileStockDailyData;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

public class BollUtil {
    public static boolean isShrink(BarSeries barSeries, float rate){
        int endIndex = barSeries.getEndIndex();
        if (endIndex<=31) return false;


        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
        StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closePriceIndicator,31);
        BollingerBandsMiddleIndicator mb_indicator = new BollingerBandsMiddleIndicator(sma_31_indicator);
        BollingerBandsLowerIndicator low_indicator = new BollingerBandsLowerIndicator(mb_indicator,stdIndicator);
        BollingerBandsUpperIndicator upper_indicator = new BollingerBandsUpperIndicator(mb_indicator,stdIndicator);

        float upperValue = upper_indicator.getValue(endIndex).floatValue();
        float lowValue = low_indicator.getValue(endIndex).floatValue();

        return (upperValue-lowValue)/lowValue<rate;
    }
}
