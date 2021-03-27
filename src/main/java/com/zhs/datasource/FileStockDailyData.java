package com.zhs.datasource;

import com.zhs.entities.Stock;
import com.zhs.entities.dict.StockGroup;
import com.zhs.utils.AnalysisUtil;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import com.zhs.utils.os.OsInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private static final String LEAD_STOCK_CODE_SOURCE = PropertyUtil.getProperty("white-horse-list");
    private static final String MSCI_STOCK_CODE_SOURCE = PropertyUtil.getProperty("msci-output")+"/msci-20210305-140405.txt";

    static {
        FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE,0)
                .toFormatter();
    }

    static private List<String> getMsciStockCode(){
        List<String> msciCodes = new ArrayList<>();

        File file = new File(MSCI_STOCK_CODE_SOURCE);
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String str = null;
            while((str = br.readLine())!=null){
                msciCodes.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msciCodes;
    }

    static private List<String> getMsciStockTradingFile(){
        List<String> msciStockCode = getMsciStockCode();

        List<String> files = new ArrayList<>();
        for (String code:msciStockCode){
            String stockFile = findStockFile(code);
            if(stockFile!=null){
                files.add(stockFile);
            }
        }
        return files;
    }

    static private List<String> getLeadStockCode() throws IOException {
        List<String> leadStockCodes = new ArrayList<>();
        try{
            FileInputStream inputStream = new FileInputStream(LEAD_STOCK_CODE_SOURCE);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row:sheet){
                if(row != null){
                    Cell cell = row.getCell(1);

                    String cellValue = cell.getStringCellValue();
                    if(!"代码".equals(cellValue)){
                        leadStockCodes.add(cellValue);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return leadStockCodes;
    }

    static private String findStockFile(String code){
        List<String> stockFiles = FileUtil.getStockFilesWithFullPath();
        for (String file :
                stockFiles) {
            int index = file.indexOf(code);
            if(index>=0){
                return file;
            }
        }
        return null;
    }

    static private List<String> getLeadStockTradingFile() throws IOException {
        List<String> msciStockCode = getLeadStockCode();

        List<String> files = new ArrayList<>();
        for (String code:msciStockCode){
            String stockFile = findStockFile(code);
            if(stockFile!=null){
                files.add(stockFile);
            }
        }
        return files;
    }

    static public BaseBarSeries load(String filePath){
        BaseBarSeries barSeries = new BaseBarSeries(filePath);;

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
        }catch (IOException ex){
            logger.error(ex.getMessage());
        }

        return barSeries;
    }

    static public Stock loadStock(String fileName){
        BaseBarSeries barSeries = new BaseBarSeries(fileName);;
        //String filePath = PropertyUtil.getProperty("stock-daily-data")+"/"+fileName;
        String filePath = fileName;
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

    /**
     * 获取特定分组中的股票数据
     * @param group
     * @return
     * @throws IOException
     */
    static public List<String> getStockDataPaths(StockGroup group) throws IOException {
        if(group == StockGroup.MSCI){
            return getMsciStockTradingFile();
        }
        if(group == StockGroup.LEADING){
            return getLeadStockTradingFile();
        }
        return null;
    }

    static public String[] getDataFiles(){
        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        return fileNames;
    }

    public static List<String> getStockFilesWithFullPath(){
        List<String> result = new ArrayList<>();
        String source = null;
        if (OsInfo.isMacOSX()){
            source = PropertyUtil.getProperty("stock-daily-data-macos");
        }else if (OsInfo.isWindows()){
            source = PropertyUtil.getProperty("stock-daily-data");
        }

        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));
        for(String name:fileNames){
            result.add(source + "/" + name);
        }
        return result;
    }

    public static List<String> getStockFilesWithFullPath(List<String> fileNames){
        List<String> result = new ArrayList<>();

        if(fileNames!=null && fileNames.size()>0){
            String source = PropertyUtil.getProperty("stock-daily-data");
            for(String str:fileNames){
                result.add(source+"/"+str);
            }
        }
        return result;
    }
}
