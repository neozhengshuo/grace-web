package com.zhs.utils;

import com.zhs.datasource.FileStockDailyData;
import com.zhs.entities.Foreign;
import com.zhs.utils.os.OsInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    public static void writeTxtWithStockCodeList(String fileName, List<String> content,boolean openFile){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date(System.currentTimeMillis());
        String txtFile = String.format("C:\\Users\\neozheng\\Desktop\\%s-%s.txt",fileName,simpleDateFormat.format(date));

        FileWriter fileWriter = null;
        File file = new File(txtFile);
        try{
            if(file.exists()) file.createNewFile();
            fileWriter = new FileWriter(txtFile);
            for(String str:content){
                String stockCode = str.substring(3,9);
                fileWriter.write(stockCode+"\r\n");
            }
            fileWriter.close();
            if(openFile){
                Runtime.getRuntime().exec("notepad "+txtFile);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 创建一个文本文件，将结果写入到文本文件中
     * @param fileName 文件名
     * @param stocksFileList 符合调节的股票
     * @param openFile 写入文件后是否打开
     */
    public static void writeTxtFile(String fileName,List<String> stocksFileList,boolean openFile){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date(System.currentTimeMillis());

        String txtFile = null;
        if(OsInfo.isWindows()){
            txtFile = String.format("C:\\Users\\neozheng\\Desktop\\%s-%s.txt",fileName,simpleDateFormat.format(date));
        }else if(OsInfo.isMacOSX()){
            txtFile = String.format("/Users/zhengshuo/Desktop/%s-%s.txt",fileName,simpleDateFormat.format(date));
        }

        FileWriter fileWriter = null;
        File file = new File(txtFile);
        try{
            if(file.exists()){
                file.createNewFile(); // 覆盖掉已有的文件
            }
            fileWriter = new FileWriter(txtFile);
            for(String filePath:stocksFileList){
                File tempFile = new File(filePath.trim());
                String stockCode = tempFile.getName().substring(3,9);
                fileWriter.write(stockCode+"\r\n");
            }
            fileWriter.close();
            if(openFile){
                if(OsInfo.isWindows()){
                    Runtime.getRuntime().exec("notepad "+txtFile);
                }else if(OsInfo.isMacOSX()){
                    // Runtime.getRuntime().exec("/System/Applications/TextEdit.app "+txtFile);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 获取文件的完整路径
     * @param fileNames 要获取完整路径的文件列表
     * @return 文件完整路径列表
     */
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

    /**
     * 获取所有的股票文件（含路径）
     * @return 文件列表
     */
    public static List<String> getStockFilesWithFullPath(){
        List<String> result = new ArrayList<>();

        String source = PropertyUtil.getProperty("stock-daily-data");
        File file = new File(source);
        String[] fileNames = file.list((dir,name)->name.endsWith(".txt"));

        for(String name:fileNames){
            result.add(source + "/" + name);
        }

        return result;
    }

    /**
     * 读取存有外资数据的Excel文件
     * @param excelFile 需要读取的Excel文件（提供完整路径）
     * @return Foreign列表
     */
    public static List<Foreign> readForeignExcel(String excelFile) throws IOException {
        List<Foreign> foreignList = new ArrayList<>();

        try{
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            Workbook wb = new HSSFWorkbook(fileInputStream);

            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet){
                if(row == null){
                    continue;
                }
                Foreign foreign = new Foreign();
                for(Cell cell : row){
                    //cell.getStringCellValue()
                }
                foreignList.add(foreign);
            }
        }catch (Exception ex){
            logger.error(ex.getMessage());
            throw ex;
        }

        return foreignList;
    }
}
