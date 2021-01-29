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
