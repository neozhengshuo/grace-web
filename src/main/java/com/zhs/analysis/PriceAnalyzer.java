package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.utils.KLineUtil;
import com.zhs.utils.PriceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.BaseBarSeries;

import java.util.ArrayList;
import java.util.List;

public class PriceAnalyzer {
    private final List<String> fileList;
    private static final Logger logger = LoggerFactory.getLogger(PriceAnalyzer.class);

    public PriceAnalyzer(List<String> fileList){
        this.fileList = fileList;
    }

    /**
     * 价格在当天突破指定的均线
     * @param ma
     * @return
     */
    public List<String> getPriceBreakUp(int ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPriceBreakUp(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 价格在特定天数内突破指定均线的。
     * @param days 天数
     * @param increase 上涨的幅度
     * @param volMa1 需要突破的均量1
     * @param volMa2 需要突破的均量2
     * @return
     */
    public List<String> isPriceIncreased(int days,float increase,int volMa1,int volMa2){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPriceIncreased(barSeries,days,increase,volMa1,volMa2);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }



    /**
     * 价格在特定日期内突破指定均线的。
     * @param ma 均线
     * @param days 表示多少天前。如果为零则表示当天。
     * @return
     */
    public List<String> getPriceBreakUp(int ma,int days){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPriceBreakUp(barSeries,ma,days);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价格在指定天数之前的价格（扣抵判断）
     * @param day
     * @return
     */
    public List<String> getPositionAbove(int day){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPositionAbove(barSeries,day);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价格在指定天数之前的价格（扣抵判断）
     * @param day
     * @return
     */
    public List<String> getPositionUnder(int day){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isPositionUnder(barSeries,day);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当天价格是否上涨。
     * @return
     */
    public List<String> getCurrentPriceUp(){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isCurrentPriceUp(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价接触到指定均线，但收盘价在指定均线之上
     * */
    public List<String> getCurrentPriceTouchMa(int ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isCurrentPriceTouchMa(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价格在指定均线的上方
     * */
    public List<String> getCurrentPriceAboveMa(int ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isCurrentPriceAboveMa(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当前价格在指定均线的下方
     * */
    public List<String> getCurrentPriceUnderMa(int ma){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isCurrentPriceUnderMa(barSeries,ma);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 当天的K是否为可靠的拐点。
     * @return
     */
    public List<String> getReliableTurningPoint(){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isReliableTurningPoint(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }

    /**
     * 判断当天的K是否为可靠的拐点(加入实体判断)。
     * @return
     */
    public List<String> getReliableTurningPointWithRealBody(){
        List<String> results = new ArrayList<>();
        for (String file:this.fileList){
            BaseBarSeries barSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));
            boolean hit = PriceUnit.isReliableTurningPointWithRealBody(barSeries);
            if(hit){
                results.add(file);
            }
        }
        return results;
    }
}
