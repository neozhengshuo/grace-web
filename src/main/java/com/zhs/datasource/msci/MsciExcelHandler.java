package com.zhs.datasource.msci;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MsciExcelHandler {
    private static final Logger logger = LoggerFactory.getLogger(MsciExcelHandler.class);
    private static final String regEx = "[^0-9]";

    public static List<String[]> getMsciStock(String excelDirPath) throws IOException {
        List<String[]> codeAndNameList = extractStockCodeAndName(excelDirPath);
        return codeAndNameList.stream().distinct().collect(Collectors.toList());
    }

    public static List<String[]> extractStockCodeAndName(String excelDirPath) throws IOException {
        List<String[]> codeAndNameList = new ArrayList<>();
        try{
            File file = new File(excelDirPath);
            String[] files = file.list((dir,name)->name.endsWith(".xlsx"));
            for (String f:files){
                FileInputStream inputStream = new FileInputStream(excelDirPath+"\\"+f);
                Workbook wb = WorkbookFactory.create(inputStream);
                Sheet sheet = wb.getSheetAt(0);
                for (Row row : sheet){
                    if(row != null){
                        Cell cell = row.getCell(0);
                        if (cell != null){
                            String cel = row.getCell(0).getStringCellValue();
                            String[] cn = getCodeAndName(cel);
                            codeAndNameList.add(cn);
                            logger.info(cn[0]+" && "+cn[1]);
                        }
                    }
                }
            }
        } catch (IOException ex){
            logger.error(ex.getMessage());
            throw ex;
        }
        return codeAndNameList;
    }

    private static String[] getCodeAndName(String cellValue){
        String[] codeAndName = new String[2];
        if (cellValue != null || cellValue.trim().length() != 0){
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(cellValue);
            codeAndName[0] = m.replaceAll("").trim();
            codeAndName[1] = cellValue.trim().replaceFirst(codeAndName[0],"");
        }
        return codeAndName;
    }
}
