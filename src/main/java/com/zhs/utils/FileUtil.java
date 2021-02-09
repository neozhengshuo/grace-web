package com.zhs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
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
        String txtFile =
                String.format("C:\\Users\\neozheng\\Desktop\\%s-%s.txt",fileName,simpleDateFormat.format(date));

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
                Runtime.getRuntime().exec("notepad "+txtFile);
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
     * @return
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
}
