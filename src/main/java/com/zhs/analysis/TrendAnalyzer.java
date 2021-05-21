package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.StockFeatures;
import com.zhs.entities.dict.KStickPosition;
import com.zhs.entities.dict.MovingAverage;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.MovingAverageUtil;
import com.zhs.utils.PlatformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * 趋势分析，以趋势向上为基础配合其他指标（boll,kdj等）选出需要关注的股票。
 */
public class TrendAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(TrendAnalyzer.class);
    private static final AnalysisUtil analysisUtil = new AnalysisUtil();
    private static final MovingAverageUtil movingAverageUtil = new MovingAverageUtil();
    private List<String> trendUpList;

    /**
     * 实例化一个TrendAnalyzer类的新对象
     */
    public TrendAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 通过指定的天数和距离获取平台
     * @param platformDays
     * @param distance
     * @return
     */
    public List<String> getPlatform(int platformDays,float distance){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PlatformUtil.isPlatform(baseBarSeries,platformDays,distance);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取趋势向上的股票（MA31、MA63、MA250）
     */
    public List<String> loadTrendUp(){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean isHit1 = analysisUtil.isTrendUpwards(baseBarSeries,31,10);
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,63,20);
            boolean isHit3 = analysisUtil.isTrendUpwards(baseBarSeries,250,50);
            if(isHit1 && isHit2 && isHit3){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get250MaTrendUp(){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is250MaTrendUpwards(baseBarSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get63MaTrendUp(){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is63MaTrendUpwards(baseBarSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get31MaTrendUp(){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is31MaTrendUpwards(baseBarSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getMaTrendUp(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isMaTrendUpwards(baseBarSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 判断两条均线的距离是否小于指定的值
     * @param ma1
     * @param ma2
     * @param distance
     * @return
     */
    public List<String> getMaDistance(MovingAverage ma1,MovingAverage ma2,float distance){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isMaDistance(baseBarSeries,ma1,ma2,distance);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getMaTrendSmooth(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isMaTrendSmooth(baseBarSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 对三条均线进行比较，某条均线是否在其他两条均线的中间。
     */
    public List<String> getMaTrendBetween(MovingAverage upMa, MovingAverage midMa, MovingAverage downMa){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isTrendBetween(baseBarSeries,upMa,midMa,downMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }




    public List<String> getMaTrendDown(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isMaTrendDown(baseBarSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 下影线，收红
     * @return
     */
    public List<String> getLowerShadow(){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isLowerShadow(baseBarSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * [价]在指定的两条均线之间
     * @param aboveMa
     * @param belowMa
     * @return
     */
    public List<String> getPriceBetweenMa(MovingAverage aboveMa,MovingAverage belowMa){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isPriceBetweenMa(baseBarSeries,aboveMa,belowMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 股价是在某条均线的下面
     * @param ma
     * @return
     */
    public List<String> getPriceUnderMa(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isPriceUnderMa(baseBarSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 判断股价是否在某条均线的上面
     * @param ma
     * @return
     */
    public List<String> getPriceAboveMa(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isPriceAboveMa(baseBarSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get_ema_up(MovingAverage ma ,int continued){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_ema_up(barSeries,ma,continued);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 价格突破指定的EMA
     * @param ma
     * @return
     */
    public List<String> get_price_up_ema(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_up_ema(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价离均线的距离
     * @param ma
     * @return
     */
    public List<String> get_price_distance_ema(MovingAverage ma,float distance){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_distance_ema(barSeries,ma,distance);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> getMaGoldCross(MovingAverage shortMA, MovingAverage longMA){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isMaGoldCross(barSeries,shortMA,longMA);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 第一条均线在第二条均线的下面。。
     * @param firstMa
     * @param secondMa
     * @return
     */
    public List<String> getMaPositionBelow(MovingAverage firstMa, MovingAverage secondMa){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isMaPositionBelow(barSeries,firstMa,secondMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 第一条均线在第二条均线的上面。。
     * @param firstMa
     * @param secondMa
     * @return
     */
    public List<String> getMaPositionAbove(MovingAverage firstMa, MovingAverage secondMa){
        List<String> results = new ArrayList<>();
        for(String file:fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isMaPositionAbove(barSeries,firstMa,secondMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取特定均线向上的股票。
     * @param ma 均线
     * @param continued 持续向上的天数
     * @return 股票代码列表
     */
    public List<String> getTrendUp(MovingAverage ma,int continued){
        List<String> results = new ArrayList<>();
        int intMa = ma.getMaValue();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isTrendUpwards(baseBarSeries,intMa,continued);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取特定2条均线向上的股票，且大于250MA
     * @param ma1 均线1
     * @param ma2 均线1
     * @param continued1 均线1的持续天数
     * @param continued2 均线2的持续天数
     * @return 股票代码列表
     */
    public List<String> getTrendUp(MovingAverage ma1,MovingAverage ma2,int continued1,int continued2){
        List<String> results = new ArrayList<>();
        int intMa1 = ma1.getMaValue();
        int intMa2 = ma2.getMaValue();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit1 = analysisUtil.isTrendUpwards(baseBarSeries,intMa1,continued1);
            boolean hit2 = analysisUtil.isTrendUpwards(baseBarSeries,intMa2,continued2);
            if(hit1 && hit2){
                results.add(file);
            }
        }
        return results;
    }


    /**
     * 获取特定3条均线向上的股票。
     * @param ma1 均线1
     * @param ma2 均线2
     * @param ma3 均线3
     * @param continued1 均线1的持续天数
     * @param continued2 均线2的持续天数
     * @param continued3 均线3的持续天数
     * @return 股票代码列表
     */
    public List<String> getTrendUp(MovingAverage ma1,MovingAverage ma2,MovingAverage ma3,int continued1,int continued2,int continued3){
        List<String> results = new ArrayList<>();
        int intMa1 = ma1.getMaValue();
        int intMa2 = ma2.getMaValue();
        int intMa3 = ma3.getMaValue();
        for (String file:fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit1 = analysisUtil.isTrendUpwards(baseBarSeries,intMa1,continued1);
            boolean hit2 = analysisUtil.isTrendUpwards(baseBarSeries,intMa2,continued2);
            boolean hit3 = analysisUtil.isTrendUpwards(baseBarSeries,intMa3,continued3);
            if(hit1 && hit2 && hit3){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得MA63向上，且MA31大于MA63，且MA31和MA63大于MA250的股票
     * @param continued MA63向上持续的天数
     * @return 满足调节的股票列表
     */
    public List<String> getTrendUp_MA63(int continued){
        List<String> results = new ArrayList<>();
        int ma63 = MovingAverage.MA63.getMaValue();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isTrendUpward_MA63(barSeries,continued);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得最近3天任何1天的成交量大于63MA
     * @param ma
     * @return
     */
    public List<String> getVolumeMoveThen(MovingAverage ma){
        List<String> results = new ArrayList<>();
        int maValue = ma.getMaValue();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isVolumeMoveThen(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得当前收盘价大于指定均线的股票
     * @param ma ma
     * @return
     */
    public List<String> getClosePriceMoveThen(MovingAverage ma){
        List<String> results = new ArrayList<>();
        int maValue = ma.getMaValue();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isClosePriceMoveThen(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得KD金叉的股票
     * @return
     */
    public List<String> getKdCrossUp(){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isKdCrossUp(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得K值向上的股票
     * @return
     */
    public List<String> get_k_or_j_up(){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_k_or_j_up(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得均线纠结的股票
     * @param period 周期，在指定的交易日内进行判断
     * @param frequency 满足条件的次数（多周期均线在最高价和最低价之间）。
     * @return
     */
    public List<String> get_moving_average_tangled(int period,int frequency){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_moving_average_tangled(barSeries,period,frequency);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * boll走平
     * @param continued 持续天数
     * @param rate 波动率，取值在1以下比较合适。
     * @return
     */
    public List<String> get_boll_parallel(int continued,float rate){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_boll_parallel(barSeries,continued,rate);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 量缩价稳。计算逻辑：取一定天数内每天的最低价，计算每天最低价相对于前一天的浮动率，且每日量能小于5日均量，且红K的数量大于绿K的数量。
     * <br/>放大参数也可以用于查找均线纠结的股票
     * @param continued 持续的天数
     * @param floatingRate 最低价的浮动率（一般取值在0.5之间）
     * @return
     */
    public List<String> get_moving_average_smooth(MovingAverage ma,int continued, float floatingRate){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_moving_average_smooth(barSeries,ma,continued,floatingRate);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get_price_under_ma(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_under_ma(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get_price_up_ma(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_up_ma(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 最低价接触到指定的均线（收盘价在均线之上）
     * @param ma
     * @return
     */
    public List<String> get_lower_price_touch_ma(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_lower_price_touch_ma(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 均线相等
     * @param ma1
     * @param ma2
     * @return
     */
    public List<String> getMaEqual(MovingAverage ma1,MovingAverage ma2){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = movingAverageUtil.isMaEqual(barSeries,ma1,ma2);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get_is_price_between_ma(MovingAverage upperMa,MovingAverage lowerMa){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_between_ma(barSeries,upperMa,lowerMa);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    public List<String> get_price_overgo(int days,MovingAverage ma1,MovingAverage ma2,MovingAverage ma3){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_price_overgo(barSeries,days,ma1,ma2,ma3);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }


    /**
     * 量缩价跌（最好连续2-3日），且价在31MA中间收红K
     * @param continued
     * @param ma
     * @return
     */
    public List<String> get_vol_price_low(int continued,MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.is_vol_price_low(barSeries,continued,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获得开盘价或最低价是否小于31MA的股票
     * @param ma
     * @return
     */
    public List<String> getOpenPriceLowerPriceLessThan(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isOpenPriceLowerPriceLessThan(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }


    /**
     * 最近三天最低价或收盘价在31MA之下，但当天收盘价在MA31上方
     * @param ma
     * @return
     */
    public List<String> getClosePriceLowerPriceLessThan(MovingAverage ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = analysisUtil.isClosePriceLowerPriceLessThan(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取趋势向上（MA31、MA63、MA250）且最低价在中轨以下的股票(以趋势向上为基础)
     * @return 结果
     */
    public List<String> getTrendUpBollMid(){
        List<String> results = new ArrayList<>();
        if(this.trendUpList == null || this.trendUpList.size()<=0){
            this.trendUpList = this.loadTrendUp();
        }
        for(String file:this.trendUpList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.isBollMid(baseBarSeries);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 获取J值在指定值之下的股票
     * @param jValue
     * @return
     */
    public List<String> getJLow(float jValue){
        List<String> results = new ArrayList<>();
        if(this.fileList == null || this.fileList.size()<=0){
            return results;
        }
        for (String file:this.fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.isJLower(baseBarSeries,jValue);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 查找长K棒。
     * @return
     */
    public List<String> get_long_k_stick(float multiple,int withInDays, KStickPosition kStickPosition){
        List<String> results = new ArrayList<>();
        if(this.fileList == null || this.fileList.size()<=0){
            return results;
        }
        for (String file:this.fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.is_long_k_stick(baseBarSeries,multiple,withInDays,kStickPosition);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 查找指定的天数内出现暴大量
     * @param multiple
     * @param withInDays
     * @param kStickPosition
     * @return
     */
    public List<String> get_large_volume(int withInDays, int beforeAfterDays){
        List<String> results = new ArrayList<>();
        if(this.fileList == null || this.fileList.size()<=0){
            return results;
        }
        for (String file:this.fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            boolean isHit = analysisUtil.is_large_volume(baseBarSeries,withInDays,beforeAfterDays);
            if(isHit){
                results.add(file);
            }
        }
        return results;
    }

    public List<StockFeatures> get_large_volume_list(int withInDays){
        List<StockFeatures> results = new ArrayList<>();
        if(this.fileList == null || this.fileList.size()<=0){
            return results;
        }
        for (String file:this.fileList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            StockFeatures stockFeatures = analysisUtil.get_large_volume(baseBarSeries,withInDays);
            if(stockFeatures!=null){
                results.add(stockFeatures);
            }
        }
        return results;
    }


    /**
     * 严格的长红K棒
     * @param multiple 指定长红K是前面K棒的倍数
     * @return
     */
//    public List<String> get_long_k_stick(float multiple){
//        List<String> results = new ArrayList<>();
//        if(this.fileList == null || this.fileList.size()<=0){
//            return results;
//        }
//        for (String file:this.fileList){
//            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
//            boolean isHit = analysisUtil.is_strict_long_k_stick(baseBarSeries,multiple);
//            if(isHit){
//                results.add(file);
//            }
//        }
//        return results;
//    }
}
