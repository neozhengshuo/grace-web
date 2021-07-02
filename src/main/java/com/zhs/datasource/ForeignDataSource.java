package com.zhs.datasource;

import com.zhs.entities.Foreign;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.compress.utils.Iterators;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
//import org.apache.poi.ooxml.

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ForeignDataSource {

    private static DateTimeFormatter FORMATTER;

    static {
        FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE,0)
                .toFormatter();
    }


    /**
     * 将单个Excel文件写到Cvs文件
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public static void writeSingleForeignDataToCsv(String file) throws IOException, ParseException {
        String outputDir = PropertyUtil.getProperty("foreign-processed-output");
        List<Foreign> foreignList = FileUtil.readForeignExcel(file);
        System.out.println(String.format("读取外资Excel，共%s条记录：%s,",foreignList.size(),file));

        // 从文件名获取日期
        //
        String strDate = file.substring(file.length()-13,file.length()-5);
        Date date = new SimpleDateFormat("yyyyMMdd").parse(strDate);
        strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        for (Foreign foreign:foreignList){
            String fileName = ForeignDataSource.generateOutFileName(foreign);
            String fileFullPath = outputDir+"/"+fileName;

            // 如果文件不存在则创建新文件写头。
            //
            File csvFile = new File(fileFullPath);
            if(!csvFile.exists()){
                boolean s = csvFile.createNewFile();
                FileWriter fileWriter = new FileWriter(fileFullPath);
                fileWriter.write(
                        String.format("%s,%s,%s,%s,%s\r\n",
                                "date","code","name","quantity","percentage"
                        ));
                fileWriter.close();
            }

            // 根据编码查找文件
            //
            String code = foreign.getCode();
            String targetFile = ForeignDataSource.findCsvFile(code);
            if(targetFile!=null){
                // 写入数据
                //
                FileWriter fileWriter = new FileWriter(fileFullPath,true);
                String name = foreign.getName();
                double quantity = foreign.getQuantity();
                double percentage = foreign.getPercentage();

                fileWriter.write(String.format("%s,%s,%s,%s,%s\r\n",
                        strDate,code,name,quantity,percentage));
                fileWriter.close();
            }
        }
    }

    public static void writeAllForeignDataToCsv() throws IOException, ParseException {
        List<String> excelFiles = ForeignDataSource.getExcelFileList();
        for (String file:excelFiles){
            ForeignDataSource.writeSingleForeignDataToCsv(file);
        }
    }

    /**
     * 查找csv文件
     * @param code
     * @return
     */
    private static String findCsvFile(String code){
        List<String> files = ForeignDataSource.getForeignCsvFileList();
        for (String file:files){
            int i = file.indexOf(code);
            if(i>=0){
                return file;
            }
        }
        return null;
    }

    /**
     * 生成csv文件名
     * @param foreign
     * @return
     */
    private static String generateOutFileName(Foreign foreign){
        return String.format("%s.csv",foreign.getCode());
    }

    /**
     * 获取所有的外资文件列表
     * @return
     */
    public static List<String> getExcelFileList(){
        List<String> result = new ArrayList<>();
        String source = PropertyUtil.getProperty("foreign-daily-data");

        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".xlsx"));
        assert fileNames != null;

        for(String name:fileNames){
            result.add(source + "/" + name);
        }
        return result;
    }

    /**
     * 获取所有的CSV文件列表
     * @return
     */
    public static List<String> getForeignCsvFileList(){
        List<String> result = new ArrayList<>();
        String source = PropertyUtil.getProperty("foreign-processed-output");

        File file = new File(source);

        String[] fileNames = file.list((dir,name)->name.endsWith(".csv"));
        assert fileNames != null;

        for(String name:fileNames){
            result.add(source + "/" + name);
        }
        return result;
    }

    public static BarSeries loadCsv(String file) throws IOException, ParseException {
        BaseBarSeries barSeries = new BaseBarSeries(file);

        try(FileReader reader = new  FileReader(file)){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            List<CSVRecord> csvRecords = IteratorUtils.toList(records.iterator());

            for (int i = 1;i<=csvRecords.size()-1;i++){
                String a = csvRecords.get(i).get(0);
                Date b = new SimpleDateFormat("yyyy-MM-dd").parse(a);
                Instant instant = b.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();

//                LocalDateTime dt = LocalDateTime.parse(a,FORMATTER);
                LocalDateTime dt = instant.atZone(zoneId).toLocalDateTime();
                ZonedDateTime dateTime = ZonedDateTime.of(dt, ZoneId.systemDefault());
                BaseBar bar = new BaseBar(
                        Duration.ofDays(1),
                        dateTime,
                        csvRecords.get(i).get(3),
                        csvRecords.get(i).get(3),
                        csvRecords.get(i).get(3),
                        csvRecords.get(i).get(3),
                        csvRecords.get(i).get(4));
                barSeries.addBar(bar);
            }
        }catch (IOException | ParseException ex){
            throw ex;
        }

        return barSeries;
    }
}
