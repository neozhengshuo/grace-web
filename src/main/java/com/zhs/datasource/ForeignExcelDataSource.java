package com.zhs.datasource;

import com.zhs.entities.Foreign;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.ooxml.


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ForeignExcelDataSource {

    public static void writeForeignDataToTxt() throws IOException {
        List<String> excelFiles = ForeignExcelDataSource.getExcelFileList();
        for (String file:excelFiles){
            List<Foreign> foreignList = FileUtil.readForeignExcel(file);
            System.out.println(file+"  "+foreignList.size());
        }
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
}
