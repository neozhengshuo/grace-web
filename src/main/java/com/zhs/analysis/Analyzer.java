package com.zhs.analysis;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.dict.Boll;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.PropertyUtil;
import org.hibernate.collection.internal.PersistentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;

import java.io.File;
import java.util.*;

public class Analyzer {
    private static final Logger logger = LoggerFactory.getLogger(Analyzer.class);

    /**
     * 获得移动平均线在指定天数内持续上升的股票
     * @param fileFullPaths 文件的全路径
     * @param ma 移动平均线
     * @param continued 持续的天数
     * @return 符合条件的股票
     */
    public List<String> getTrendUpwards(List<String> fileFullPaths,int ma,int continued){
        List<String> results = new ArrayList<>();

        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String filePath : fileFullPaths){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(filePath);
            logger.info(String.format("Loaded %s",filePath));
            boolean hit1 = analysisUtil.isTrendUpwards(baseBarSeries,ma,continued);
            if(hit1){
                results.add(filePath);
            }
        }
        return results;
    }

    /**
     * 前一天5MA向下，当天5MA向上
     * @param fileFullPaths 待分析的股票
     * @return 结果
     */
    public List<String> get5MAUp(List<String> fileFullPaths){
        List<String> results = new ArrayList<>();

        for(String file:fileFullPaths){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(file);
            logger.info(String.format("Loaded %s",file));

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<2) continue;

            ClosePriceIndicator closePriceIndicator =
                    new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_5_Indicator = new SMAIndicator(closePriceIndicator,5);
            float current_ma5 = sma_5_Indicator.getValue(endIndex).floatValue();
            float previous1_ma5 = sma_5_Indicator.getValue(endIndex-1).floatValue();
            float previous2_ma5 = sma_5_Indicator.getValue(endIndex-1-1).floatValue();

            boolean isHit1 = current_ma5>previous1_ma5;
            boolean isHit2 = previous1_ma5<=previous2_ma5;

            if(isHit1 && isHit2){
                results.add(file);
            }
        }

        return results;
    }

    /**
     * 获取均线纠结的股票
     * @param fileFullPaths 待分析的股票文件
     * @return
     */
    public List<String> getMovingAverageTangled(List<String> fileFullPaths,int tangledDays){
        List<String> resultList = new ArrayList<>();

        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String filePath : fileFullPaths){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(filePath);
            logger.info(String.format("Loaded %s",filePath));
            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<tangledDays){
                continue;
            }
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_5_indicator = new SMAIndicator(closePriceIndicator,5);
            SMAIndicator sma_11_indicator = new SMAIndicator(closePriceIndicator,11);
            SMAIndicator sma_18_indicator = new SMAIndicator(closePriceIndicator,18);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
            SMAIndicator sma_63_indicator = new SMAIndicator(closePriceIndicator,63);
            SMAIndicator sma_250_indicator = new SMAIndicator(closePriceIndicator,250);

            // 获取指定天数内的最高价和最低价
            //
            List<Float> highPriceList = new ArrayList<>();
            List<Float> lowPriceList = new ArrayList<>();
            for(int i = endIndex;i>=endIndex-tangledDays;i--){
                Bar bar = baseBarSeries.getBar(i);
                highPriceList.add(bar.getHighPrice().floatValue());
                lowPriceList.add(bar.getLowPrice().floatValue());
            }
            float highPrice = Collections.max(highPriceList);
            float lowPrice = Collections.min(lowPriceList);

            // 判断各均线指标是否在最低价和最高价之间
            //
            boolean isHit1 = true;
            for(int i = endIndex;i>=endIndex-tangledDays;i--){
                float ma5 = sma_5_indicator.getValue(i).floatValue();
                float ma11 = sma_11_indicator.getValue(i).floatValue();
                float ma18 = sma_18_indicator.getValue(i).floatValue();
                float ma31 = sma_31_indicator.getValue(i).floatValue();
                float ma63 = sma_63_indicator.getValue(i).floatValue();
                float ma250 = sma_250_indicator.getValue(i).floatValue();

                isHit1 = (ma18<=highPrice && ma18 >= lowPrice)
                        && (ma31<=highPrice && ma31 >= lowPrice)
                        && (ma63<=highPrice && ma63 >= lowPrice)
                        && (ma11<=highPrice && ma11 >= lowPrice)
                        && (ma250<=highPrice && ma250 >= lowPrice)
                        && (ma5<=highPrice && ma5 >= lowPrice);
                if(!isHit1){
                    break;
                }
            }
            if(isHit1){
                resultList.add(filePath);
            }
        }
        return resultList;
    }

    /**
     * 获得趋势向上的Stock(31MA、63MA向上)
     * @param ma31Continued 31MA持续向上的天数
     * @param ma63Continued 63MA持续向上的天数
     * @return 符合条件的股票
     */
    public List<String> getTrendUpwardsMa31Ma63(int ma31Continued, int ma63Continued) {
        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        List<String> upList = new ArrayList<>();

        int counter = 0;
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,31,ma31Continued);
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,63,ma63Continued);
            //boolean isHit3 = analysisUtil.isTrendUpwards(baseBarSeries,63,31);
            if(isHit
                    && isHit2
                    //&& isHit3
            ){
                upList.add(fileName);
                counter++;
            }
        }
        logger.info(String.format("分析比：%s/%s",counter,fileNames.length));
        return upList;
    }

    public List<String> getTrendUpwardsMa63Ma250(int ma63Continued, int ma250Continued) {
        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        List<String> upList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,63,ma63Continued);
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,250,ma250Continued);
            //boolean isHit3 = analysisUtil.isTrendUpwards(baseBarSeries,63,31);
            if(isHit
                    && isHit2
                //&& isHit3
            ){
                upList.add(fileName);

            }
        }
        return upList;
    }

    /**
     * 趋势向上(MA63,MA250),MA18下弯,当日价收红
     * @return 符合条件的股票
     */
    public List<String> getTrendUpwardsMa63Ma250WhitMa18Down(){
        List<String> stockNames = new ArrayList<>();
        List<String> fileNames = this.getTrendUpwardsMa63Ma250(20,50);
        if(fileNames.size()==0) return stockNames;
        String source = PropertyUtil.getProperty("stock-daily-data");
        for(String fileName:fileNames){
            String fileFullPath = source+"/"+fileName;
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(baseBarSeries);
            SMAIndicator smaIndicator =new SMAIndicator(closePriceIndicator,18);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<2) continue;

            float ma18_current = smaIndicator.getValue(endIndex).floatValue();
            float ma18_previous = smaIndicator.getValue(endIndex-1).floatValue();

            float close_current = baseBarSeries.getBar(endIndex).getClosePrice().floatValue();
            float open_current = baseBarSeries.getBar(endIndex).getOpenPrice().floatValue();

            boolean hit1 = close_current>open_current;
            boolean hit2 = ma18_current<ma18_previous;
            if(hit1 && hit2)
                stockNames.add(fileName);
        }
        return stockNames;
    }

    /**
    * 趋势向上（63MA、250MA）买入三法。(此类股票一般表现为弱势整理，在boll下轨获得支撑，即季线买点2的位置)
    * 63MA和250MA趋势向上，量达到5日均量，KD金叉
    * */
    public List<String> getTrendUpwardsAndPriceLowerWith63Ma(){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        List<String> upList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,63,18);  // 31ma向上
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,250,31);// 63ma向上
            // boolean isHit3 = analysisUtil.isUpWithVolumeMa(baseBarSeries,5);           // 当前量大于5日均量
            // boolean isHit4 = analysisUtil.isKdGoldCross(baseBarSeries);                     // 出现KD金叉
            boolean isHit5 = analysisUtil.isBollLower(baseBarSeries);                       // 最低价小于boll低轨
            boolean isHit6 = analysisUtil.isKdjUp(baseBarSeries);
            if(isHit && isHit2 && isHit5 && isHit6){
                stockList.add(fileName);
            }
        }
        return stockList;
    }

    /**
     * 趋势向上（31MA、63MA），J值低档向上。(此类股票一般表现为强势整理，在boll中轨获得支撑，即月线买点2的位置)
     * @return
     */
    public List<String> getTrendUpwardsAndPriceLowerWith31Ma(){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        List<String> upList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,31,10);  // 31ma向上
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,63,18);// 63ma向上
             boolean isHit3 = analysisUtil.isUpWithVolumeMa(baseBarSeries,5);           // 当前量大于5日均量
            // boolean isHit4 = analysisUtil.isKdGoldCross(baseBarSeries);                     // 出现KD金叉
            // boolean isHit5 = analysisUtil.isBollLower(baseBarSeries);                       // 最低价小于boll低轨
            boolean isHit6 = analysisUtil.isKdjUp(baseBarSeries);                            // J值当天向上
            if(isHit && isHit2 && isHit6 && isHit3){
                stockList.add(fileName);
            }
        }
        return stockList;
    }

    /**
     * 严选：趋势向上（31MA、63MA）,J值在低档、最低价在Boll中轨下方、量小于5日均量
     * @return
     */
    public List<String> getTrendUpwards(){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        List<String> upList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);

            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,31,10);  // 31ma向上
            if(isHit){
                boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,63,20);// 63ma向上
                if(isHit2){
                    boolean isHit3 = analysisUtil.isBollMid(baseBarSeries);                       // 最低价小于boll中轨
                    if(isHit3){
                        boolean isHit4 = analysisUtil.isJUnder(baseBarSeries,20);
                        if(isHit4){
                            stockList.add(fileName);
                        }
                    }
                }
            }
        }
        return stockList;
    }

    /**
     * 趋势向上，成交量在指定的天数内萎缩，当天J值在低档向上。
     * @param continuedDays 成交量持续缩量的天数。
     * @return 符合条件的股票
     */
    public List<String> getTrendUpwardsVolumeDrop(int continuedDays){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<continuedDays) continue;
            boolean hit = analysisUtil.isUpWithVolumeMa(baseBarSeries,5,3);

            if(hit){
                boolean hit0 = analysisUtil.isTrendUpwards(baseBarSeries,31,10);  // 31ma向上
                boolean hit1 = analysisUtil.isTrendUpwards(baseBarSeries,63,18);// 63ma向上
                boolean hit2 = analysisUtil.isKdjUp(baseBarSeries);
                if(hit0 && hit1 && hit2){
                    stockList.add(fileName);
                }
            }
        }
        return stockList;
    }

    /**
     * 获取指定天数内放量的股票
     * @param inDays 在指定的天数内放量
     * @return 符合条件的股票
     */
    public List<String> getStockWithEnlargeVolume(int inDays){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();

        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            boolean hit0 = analysisUtil.isTrendUpwards(baseBarSeries,31,10);
            if(hit0){
                boolean hit1 = analysisUtil.isTrendUpwards(baseBarSeries,63,20);
                if(hit1){
                    boolean hit2 = analysisUtil.enlargeVolume(baseBarSeries,inDays);
                    if(hit2){
                        stockList.add(fileName);
                    }
                }
            }
        }
        return stockList;
    }

    // ---- 均线金叉（18MA、31MA和31MA、63MA）
    //
    public List<String> maLineGoldCross(){

        List<String> stockFileList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        // 计算趋势向上（63MA、250MA）
        //
        List<String> upFilesList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String str:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(str);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,63,5);  // 31ma向上
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,250,20);// 63ma向上
            if(isHit && isHit2)
                upFilesList.add(str);
        }

        int ma05 = 5;
        int ma18 = 18;
        int ma31 = 31;
        int ma63 = 63;

        for(String f:upFilesList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(f);
            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<3) continue;

            ClosePriceIndicator closePriceIndicator =new ClosePriceIndicator(baseBarSeries);
            SMAIndicator ma05Indicator = new SMAIndicator(closePriceIndicator,ma05);
            SMAIndicator ma18Indicator = new SMAIndicator(closePriceIndicator,ma18);
            SMAIndicator ma31Indicator = new SMAIndicator(closePriceIndicator,ma31);
            SMAIndicator ma63Indicator = new SMAIndicator(closePriceIndicator,ma63);

            float currentMa05 = ma05Indicator.getValue(endIndex).floatValue();
            float previous1Ma05 = ma05Indicator.getValue(endIndex-1).floatValue();
            float previous2Ma05 = ma05Indicator.getValue(endIndex-1-1).floatValue();

            float currentMa18 = ma18Indicator.getValue(endIndex).floatValue();
            float previous1Ma18 = ma18Indicator.getValue(endIndex-1).floatValue();
            float previous2Ma18 = ma18Indicator.getValue(endIndex-1-1).floatValue();

            float currentMa31 = ma31Indicator.getValue(endIndex).floatValue();
            float previous1Ma31 = ma31Indicator.getValue(endIndex-1).floatValue();
            float previous2Ma31 = ma31Indicator.getValue(endIndex-1-1).floatValue();

            float currentMa63 = ma63Indicator.getValue(endIndex).floatValue();
            float previous1Ma63 = ma63Indicator.getValue(endIndex-1).floatValue();
            float previous2Ma63 = ma63Indicator.getValue(endIndex-1-1).floatValue();

            boolean hit0 = currentMa05>=currentMa18 && previous1Ma05<=previous1Ma18;
            boolean hit1 = currentMa05>=currentMa31 && previous1Ma05<=previous1Ma31;
            boolean hit2 = currentMa18>=currentMa31 && previous1Ma18<=previous1Ma31;

            boolean hit3 = analysisUtil.isUpWithVolumeMa(baseBarSeries,5);
            boolean hit4 = analysisUtil.isUpWithVolumeMa(baseBarSeries,63);

            boolean hit5 = baseBarSeries.getBar(endIndex).getOpenPrice().floatValue()
                    <= baseBarSeries.getBar(endIndex).getClosePrice().floatValue();



//            boolean hit3 = currentMa31>=currentMa63 && previous1Ma31<=previous1Ma63;
//            boolean hit4 = previous2Ma31<=previous2Ma63 && previous1Ma31>=previous1Ma63;

            if((hit0 || hit1 || hit2) && (hit3 && hit4) && hit5){
                stockFileList.add(baseBarSeries.getName());
            }
        }

        return stockFileList;
    }

    public List<String> bollLower(){
        List<String> stockFileList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        // 计算趋势向上（63MA、250MA）
        //
        List<String> upFilesList = new ArrayList<>();
        AnalysisUtil analysisUtil = new AnalysisUtil();
        for(String str:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(str);
            boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,63,20);  // 31ma向上
            boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,250,40);// 63ma向上
            if(isHit && isHit2)
                upFilesList.add(str);
        }

        for(String fileName:upFilesList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
            StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closePriceIndicator,31);
            BollingerBandsMiddleIndicator mb_indicator = new BollingerBandsMiddleIndicator(sma_31_indicator);
            BollingerBandsLowerIndicator low_indicator = new BollingerBandsLowerIndicator(mb_indicator,stdIndicator);
            BollingerBandsUpperIndicator upper_indicator = new BollingerBandsUpperIndicator(mb_indicator,stdIndicator);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<2) continue;

            float currentLowPrice = baseBarSeries.getBar(endIndex).getLowPrice().floatValue();
            float currentClosePrice = baseBarSeries.getBar(endIndex).getClosePrice().floatValue();

            float previous1Low = baseBarSeries.getBar(endIndex-1).getLowPrice().floatValue();

            float currentBollLower = low_indicator.getValue(endIndex).floatValue();
            float currentBollMiddle = mb_indicator.getValue(endIndex).floatValue();

            float previous1BollLower = low_indicator.getValue(endIndex-1).floatValue();
            float previous1BollMiddle = mb_indicator.getValue(endIndex-1-1).floatValue();

            float previous2BollLower = low_indicator.getValue(endIndex-1).floatValue();
            float previous2BollMiddle = mb_indicator.getValue(endIndex-1-1).floatValue();



            if(currentLowPrice<currentBollLower || previous1Low<previous1BollLower){
                continue;
            }

            double temp = baseBarSeries.getBar(endIndex).getClosePrice().floatValue() * 0.1;
            if(currentLowPrice-currentBollLower<=temp
                    && currentClosePrice<currentBollMiddle
                    && currentClosePrice>currentBollLower){
                stockFileList.add(fileName);
            }
        }

        return stockFileList;
    }

    /**
     * 获取股价在Boll低轨下的股票
     * @param filePaths 要判断的股票
     * @return 符合条件的股票
     */
    public List<String> getBollLower(List<String> filePaths){
        List<String> results = new ArrayList<>();
        for(String path:filePaths){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(path);
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
            StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closePriceIndicator,31);
            BollingerBandsMiddleIndicator mb_indicator = new BollingerBandsMiddleIndicator(sma_31_indicator);
            BollingerBandsLowerIndicator low_indicator = new BollingerBandsLowerIndicator(mb_indicator,stdIndicator);
            BollingerBandsUpperIndicator upper_indicator = new BollingerBandsUpperIndicator(mb_indicator,stdIndicator);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<2) continue;

            float currentLowPrice = baseBarSeries.getBar(endIndex).getLowPrice().floatValue();
            float currentLowerValue = low_indicator.getValue(endIndex).floatValue();
            boolean hit1 = currentLowPrice <= currentLowerValue;
            if(hit1){
                results.add(path);
            }
        }
        return results;
    }

    public List<String> getBoll(List<String> filePaths, Boll boll){
        List<String> results = new ArrayList<>();
        for(String path:filePaths){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(path);
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
            StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closePriceIndicator,31);
            BollingerBandsMiddleIndicator mb_indicator = new BollingerBandsMiddleIndicator(sma_31_indicator);
            BollingerBandsLowerIndicator low_indicator = new BollingerBandsLowerIndicator(mb_indicator,stdIndicator);
            BollingerBandsUpperIndicator upper_indicator = new BollingerBandsUpperIndicator(mb_indicator,stdIndicator);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<2) continue;

            switch (boll){
                case MID:
                    float currentClosePrice = baseBarSeries.getBar(endIndex).getClosePrice().floatValue();
                    float currentBollMid = mb_indicator.getValue(endIndex).floatValue();
                    boolean hit = currentClosePrice<=currentBollMid;
                    if(hit){
                        results.add(path);
                    }
                case LOWER:
                    float currentLowPrice = baseBarSeries.getBar(endIndex).getLowPrice().floatValue();
                    float currentLowerValue = low_indicator.getValue(endIndex).floatValue();
                    boolean hit1 = currentLowPrice <= currentLowerValue;
                    if(hit1){
                        results.add(path);
                    }
            }
        }
        return results;
    }

    /**
     * 横盘
     * @param continueDay 持续天数
     * @param floatingRate 浮动率（最好设置在0.01-0.075之间）。
     * @param kdValue D在某个值的下方（最好设置在50）。
     * @return 符合条件的股票
     */
    public List<String> sideWays(int continueDay,double floatingRate, float kdValue){
        List<String> stockList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();

        // 计算趋势向上（63MA、250MA）
        //
        List<String> upFilesList = new ArrayList<>();
        for(String str:fileNames){
            //BaseBarSeries baseBarSeries = FileStockDailyData.load(str);
            //boolean isHit = analysisUtil.isTrendUpwards(baseBarSeries,63,15);  // 31ma向上
            //boolean isHit2 = analysisUtil.isTrendUpwards(baseBarSeries,250,60);// 63ma向上
            //boolean isHit3 = analysisUtil.lowerKdj(baseBarSeries,kdValue);
            if(true)
                upFilesList.add(str);
        }

        if(continueDay<=0) return stockList;

        for (String fileName:upFilesList){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<continueDay){
                continue;
            }
            List<Double> maxPriceList = new ArrayList<>();
            List<Double> minPriceList = new ArrayList<>();
            for(int i=endIndex;i>endIndex-continueDay;i--){
                maxPriceList.add(baseBarSeries.getBar(i).getHighPrice().doubleValue());
                minPriceList.add(baseBarSeries.getBar(i).getLowPrice().doubleValue());
            }

            double max = Collections.max(maxPriceList);
            double min = Collections.min(maxPriceList);
            double difference = Math.abs(max-min);
            double currentClosePrice = baseBarSeries.getBar(endIndex).getClosePrice().doubleValue();
            // double average = minPriceList.stream().mapToDouble((s)->s).summaryStatistics().getAverage();

            if(difference/currentClosePrice>=0.001 && difference/currentClosePrice<=floatingRate){
                stockList.add(fileName);
            }
        }
        return stockList;
    }

    public List<String> sideWays(int continueDay,double floatingRate){
        List<String> stockFileList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();

        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);
            StandardDeviationIndicator stdIndicator = new StandardDeviationIndicator(closePriceIndicator,31);
            BollingerBandsMiddleIndicator mb_indicator = new BollingerBandsMiddleIndicator(sma_31_indicator);
            BollingerBandsLowerIndicator low_indicator = new BollingerBandsLowerIndicator(mb_indicator,stdIndicator);
            BollingerBandsUpperIndicator upper_indicator = new BollingerBandsUpperIndicator(mb_indicator,stdIndicator);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<continueDay){
                continue;
            }

            List<Double> boll_up_list = new ArrayList<>();
            List<Double> boll_low_list = new ArrayList<>();
            for(int i=endIndex;i>endIndex-continueDay;i--){
                boll_up_list.add(upper_indicator.getValue(i).doubleValue());
                boll_low_list.add(low_indicator.getValue(i).doubleValue());
            }
            double low_max = Collections.max(boll_low_list);
            double low_min = Collections.min(boll_low_list);
            double low_diff = low_max-low_min;

            double up_max = Collections.max(boll_up_list);
            double up_min = Collections.min(boll_up_list);
            double up_diff = up_max-up_min;

            double up_floating = up_diff/up_max;
            double low_floating = low_diff/low_max;

            boolean isHit1 = up_floating>0 && up_floating<=floatingRate;
            boolean isHit2 = low_floating>0 && low_floating<=floatingRate;
            if(isHit1 && isHit2){
                stockFileList.add(fileName);
            }
        }

        return stockFileList;
    }

    public List<String> movingAverageVolatility(int continueDay,float maFloatingRate)
    {
        List<String> stockFileList = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();

        for(String fileName:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(fileName);
            ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(baseBarSeries);
            SMAIndicator sma_11_indicator = new SMAIndicator(closePriceIndicator,11);
            SMAIndicator sma_31_indicator = new SMAIndicator(closePriceIndicator,31);

            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<continueDay){
                continue;
            }

            float ma11Max = 0;
            float ma11Min = 0;
            float ma31Max = 0;
            float ma31Min = 0;
            List<Float> ma11List = new ArrayList<>();
            List<Float> ma31List = new ArrayList<>();

            for(int i=endIndex;i>endIndex-continueDay;i--){
                ma11List.add(sma_11_indicator.getValue(i).floatValue());
                ma31List.add(sma_31_indicator.getValue(i).floatValue());
            }
            ma11Min = Collections.min(ma11List);
            ma11Max = Collections.max(ma11List);
            float ma11Diff = ma11Max-ma11Min;
            float ma11Floating = ma11Diff/sma_11_indicator.getValue(endIndex).floatValue();

            ma31Min = Collections.min(ma31List);
            ma31Max = Collections.max(ma31List);
            float ma31Diff = ma31Max-ma31Min;
            float ma31Floating = ma31Diff/sma_31_indicator.getValue(endIndex).floatValue();

            boolean isHit = ma11Floating>0 && ma11Floating<=maFloatingRate;
            boolean isHit1 = ma31Floating>0 && ma31Floating<=maFloatingRate;
            if(isHit && isHit1){
                stockFileList.add(fileName);
            }
        }

        return stockFileList;

    }

    public List<String> captureRedK(int daysAgo,float rate){
        List<String> stockList = new ArrayList<>();
        int temp = 0;

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        AnalysisUtil analysisUtil = new AnalysisUtil();

        for(String str:fileNames){
            BaseBarSeries baseBarSeries = FileStockDailyData.load(str);
            BaseBarSeries subBarSeries = null;
            int endIndex = baseBarSeries.getEndIndex();
            if(endIndex<=daysAgo) continue;

            if(daysAgo == 0){
                subBarSeries = baseBarSeries;
            }else{
                subBarSeries = baseBarSeries.getSubSeries(0,endIndex-(daysAgo-1));
            }

            if(daysAgo>=endIndex) continue;

            int subEndIndex = subBarSeries.getEndIndex();

            boolean isHit = analysisUtil.isTrendUpwards(subBarSeries,63,15);  // 31ma向上
            boolean isHit2 = analysisUtil.isTrendUpwards(subBarSeries,250,30);// 63ma向上

            VolumeIndicator volumeIndicator = new VolumeIndicator(subBarSeries);
            SMAIndicator sma5VolumeIndicator =new SMAIndicator(volumeIndicator,5);
            SMAIndicator sma63VolumeIndicator =new SMAIndicator(volumeIndicator,63);

            Bar bar = subBarSeries.getBar(subEndIndex);
            float openPriceCurrent = subBarSeries.getBar(subEndIndex).getOpenPrice().floatValue();
            float closePriceCurrent = subBarSeries.getBar(subEndIndex).getClosePrice().floatValue();
            float chaJia = closePriceCurrent-openPriceCurrent;
            if(chaJia<=0) continue;

            float ma5Volume = sma5VolumeIndicator.getValue(subEndIndex).floatValue();
            float ma63Volume = sma63VolumeIndicator.getValue(subEndIndex).floatValue();
            float volumeCurrent = subBarSeries.getBar(subEndIndex).getVolume().floatValue();

            boolean hit3 = chaJia/(closePriceCurrent-chaJia)>=rate;
            boolean hit4 = volumeCurrent>=ma5Volume && volumeCurrent>=ma63Volume;

            if(isHit && isHit2 && hit3 && hit4) {
                stockList.add(str);
                if(daysAgo>0) {
                    Bar bar2 = baseBarSeries.getBar(subEndIndex+1);
                    float t1 = bar.getClosePrice().floatValue();
                    float t2 = bar2.getClosePrice().floatValue();
                    if(t2>t1) {
                        temp++;
                        logger.info(String.format("【%s %s】价格浮动：%s 量能：%s",
                                str,
                                bar.getSimpleDateName(),
                                chaJia/(closePriceCurrent-chaJia),
                                volumeCurrent));
                    }else{
                        logger.info(String.format("\033[1;32m【%s %s】价格浮动：%s 量能：%s \033[0m",
                                str,
                                bar.getSimpleDateName(),
                                chaJia/(closePriceCurrent-chaJia),
                                volumeCurrent));
                    }
                }
            }
        }
        if(stockList.size()>0){
            logger.info(String.format("%s/%s=%s",temp,stockList.size(),(float)temp/stockList.size()));
        }
        return stockList;
    }

}
