package com.zhs.indicator;

import com.zhs.entities.dict.UpDown;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

public class JTurningPointIndicator extends CachedIndicator<Boolean> {
    private final JIndicator jIndicator;
    private final float j;
    private final UpDown upDown;

    public  JTurningPointIndicator(BarSeries barSeries,UpDown upDown,float j){
        super(barSeries);
        this.upDown = upDown;
        this.jIndicator = new JIndicator(new StochasticOscillatorKIndicator(barSeries,9));
        this.j = j;
    }

    @Override
    protected Boolean calculate(int i) {
        int endIndex = this.getBarSeries().getEndIndex();
        if(endIndex < 0) return false;

        float current_j = jIndicator.getValue(i).floatValue();
        float before1_j = jIndicator.getValue(i-1).floatValue();

        if(upDown == UpDown.UP){
            return current_j>before1_j && before1_j<this.j;
        }
        if(upDown == UpDown.DOWN){
            return current_j<before1_j && before1_j>this.j;
        }
        return false;
    }
}
