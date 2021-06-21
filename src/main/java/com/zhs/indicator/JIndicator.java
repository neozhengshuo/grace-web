package com.zhs.indicator;

import com.zhs.entities.Kdj;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.num.Num;

import java.util.ArrayList;
import java.util.List;

public class JIndicator extends CachedIndicator<Num> {
    private Indicator<Num> indicator;
    private List<Kdj> kdjList = null;

    public JIndicator(StochasticOscillatorKIndicator rsv) {
        super(rsv);
        this.indicator = rsv;
    }

    @Override
    protected Num calculate(int index) {
        if(kdjList == null){
            kdjList = new ArrayList<>();
            int endIndex = this.getBarSeries().getEndIndex();
            for (int i=0;i<=endIndex;i++){
                Kdj kdj = new Kdj();
                kdj.setIndex(i);
                kdj.setRsv(this.indicator.getValue(i).floatValue());
                kdj.setDateTime(this.getBarSeries().getBar(i).getSimpleDateName());
                this.kdjList.add(kdj);
                if(i == 0){
                    this.kdjList.get(i).setK(50);
                    this.kdjList.get(i).setD(50);
                    this.kdjList.get(i).setJ(50);
                }else
                {
                    float x = 2/3F;
                    float y = 1/3F;

                    // 计算K值
                    float k1 = (x*this.kdjList.get(i-1).getK());
                    float k2 = (y*this.kdjList.get(i).getRsv());
                    this.kdjList.get(i).setK(k1+k2);

                    // 计算D值
                    float d1 = (x*this.kdjList.get(i-1).getD());
                    float d2 = (y*this.kdjList.get(i).getK());
                    this.kdjList.get(i).setD(d1+d2);

                    // 计算J值
                    float j1 = 3F*this.kdjList.get(i).getK();
                    float j2 = 2F*this.kdjList.get(i).getD();
                    this.kdjList.get(i).setJ(j1-j2);
                }
            }
        }
        return numOf(kdjList.get(index).getJ());
    }
}
