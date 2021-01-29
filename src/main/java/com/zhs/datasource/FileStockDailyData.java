package com.zhs.datasource;

import com.zhs.entities.Stock;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.PropertyUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;



public class FileStockDailyData {
    private static final Logger logger = LoggerFactory.getLogger(FileStockDailyData.class);
    private static DateTimeFormatter FORMATTER;
    private static List<BaseBarSeries> stockDailyList ;

    static {
        FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE,0)
                .toFormatter();
    }

    synchronized static public List<BaseBarSeries> load(){
        logger.info("load start...");
        if(stockDailyList == null){
            stockDailyList = new ArrayList<BaseBarSeries>();
            String source = PropertyUtil.getProperty("stock-daily-data");
            File file = new File(source);
            String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
            logger.info("文件数量："+ fileNames.length);
            for(String item:fileNames){
                try(FileReader reader = new  FileReader(source+"\\"+item)){
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                    BaseBarSeries series = new BaseBarSeries(item);
                    for(CSVRecord record:records){
                        if(record.size()<7){
                            continue;
                        }
                        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDateTime dt = LocalDateTime.parse(record.get(0),FORMATTER);
                        ZonedDateTime dateTime = ZonedDateTime.of(dt, ZoneId.systemDefault());
                        logger.info("读取："+item+" "+ dateTime.toString());
                        BaseBar bar = new BaseBar(Duration.ofDays(1),dateTime,record.get(1),record.get(2),record.get(3),record.get(4),record.get(5));

                        series.addBar(bar);
                    }
                    stockDailyList.add(series);
                }catch (IOException ex){
                    logger.error(ex.getMessage());
                }
            }
        }
        return stockDailyList;
    }

    static public BaseBarSeries load(String fileName){
        BaseBarSeries barSeries = new BaseBarSeries(fileName);;
        String filePath = PropertyUtil.getProperty("stock-daily-data")+"/"+fileName;

        try(FileReader reader = new  FileReader(filePath)){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for(CSVRecord record:records){
                if(record.size()<7){
                    continue;
                }
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime dt = LocalDateTime.parse(record.get(0),FORMATTER);
                ZonedDateTime dateTime = ZonedDateTime.of(dt, ZoneId.systemDefault());
                BaseBar bar = new BaseBar(
                        Duration.ofDays(1),
                        dateTime,record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4),
                        record.get(5));
                barSeries.addBar(bar);
            }
            logger.info("loaded:"+filePath);
        }catch (IOException ex){
            logger.error(ex.getMessage());
        }

        return barSeries;
    }

    static public Stock loadStock(String fileName){
        BaseBarSeries barSeries = new BaseBarSeries(fileName);;
        String filePath = PropertyUtil.getProperty("stock-daily-data")+"/"+fileName;
        logger.info("读取："+filePath);

        try(FileReader reader = new  FileReader(filePath)){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for(CSVRecord record:records){
                if(record.size()<7){
                    continue;
                }
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime dt = LocalDateTime.parse(record.get(0),FORMATTER);
                ZonedDateTime dateTime = ZonedDateTime.of(dt, ZoneId.systemDefault());
                BaseBar bar = new BaseBar(
                        Duration.ofDays(1),
                        dateTime,
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4),
                        record.get(5));
                barSeries.addBar(bar);
            }
        }catch (IOException ex){
            logger.error(ex.getMessage());
        }
        AnalysisUtil analysisUtil = new AnalysisUtil();
        return analysisUtil.getStock(barSeries);
    }

    static public String[] dataFiles(){
        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        return fileNames;
    }
}
