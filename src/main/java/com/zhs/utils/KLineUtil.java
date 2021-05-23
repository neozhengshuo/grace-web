package com.zhs.utils;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KLineUtil {

    static public List<String> results = new ArrayList<>();

    /**
     * 长红K
     * 判断在指定的天数内是否出现长红K线,且当前价格没有跌破长K的价格
     * @param barSeries
     * @param days 指定的天数
     * @param multiple 长K相较于前一天的倍数。
     * @return
     */
    static public boolean isLongKline(BarSeries barSeries, int days, float multiple){
        int endIndex = barSeries.getEndIndex();
        if(endIndex<days) return false;

        BarSeries subBarSeries = barSeries.getSubSeries(endIndex-(days-1),endIndex+1);
        int subEndIndex = subBarSeries.getEndIndex();
        for (int i = subEndIndex;i>=1;i--){
            float current_open = subBarSeries.getBar(i).getOpenPrice().floatValue();
            float current_close = subBarSeries.getBar(i).getClosePrice().floatValue();
            float current_high = subBarSeries.getBar(i).getHighPrice().floatValue();
            float current_low = subBarSeries.getBar(i).getLowPrice().floatValue();
            if(current_close<current_open) return false; // 排除下跌的

            float before_open = subBarSeries.getBar(i-1).getOpenPrice().floatValue();
            float before_close = subBarSeries.getBar(i-1).getClosePrice().floatValue();

            // 求当天和前一天的K线实体（采用四舍五入）
            float before_entity = Math.abs(before_close-before_open);
            float current_entity = Math.abs(current_close-current_open);
            before_entity = new BigDecimal(before_entity).setScale(2, RoundingMode.HALF_UP).floatValue();
            current_entity = new BigDecimal(current_entity).setScale(2, RoundingMode.HALF_UP).floatValue();

            float cankao = current_open+(current_entity/2);
            if(before_close>=cankao) return false;

            if(current_entity/before_entity>=multiple){ // 倍数判断
                float endIndex_close = subBarSeries.getBar(subEndIndex).getClosePrice().floatValue();
                if(endIndex_close>=current_low && endIndex_close<=current_high){ // 当前价格在长红K的范围内。
                    KLineUtil.results.add(subBarSeries.getName()+"  "+subBarSeries.getBar(i).getSimpleDateName());
                    return true;
                }
            }
        }

        return false;
    }
}
