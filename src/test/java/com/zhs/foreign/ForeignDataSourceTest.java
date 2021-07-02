package com.zhs.foreign;

import com.zhs.datasource.ForeignDataSource;
import com.zhs.entities.Foreign;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForeignDataSourceTest {
    @Test
    public void test_getExcelFileList(){
        List<String>  fileList = ForeignDataSource.getExcelFileList();
        System.out.println(fileList.size());
        for (String file:fileList){
            System.out.println(file);
        }
    }

    @Test
    public void test_ReadExcel() throws IOException {
        List<Foreign> foreignList = FileUtil.readForeignExcel("E:/OneDrive/grace-web/grace-data/group/foreign/sh/foreign-sh-20210329.xlsx");
        System.out.println(foreignList.size());
        for (Foreign foreign : foreignList) {
            System.out.println(foreign);
        }
    }

    @Test
    public void test_readAllForeignExcel() throws IOException {
        List<String>  fileList = ForeignDataSource.getExcelFileList();
        System.out.println(fileList.size());
        for (String file:fileList){
            List<Foreign> foreignList = FileUtil.readForeignExcel(file);
            System.out.println(file + " " +foreignList.size());
        }
    }

    @Test
    public void test_writeSingleForeignDataToCsv() throws IOException, ParseException {
        ForeignDataSource.writeSingleForeignDataToCsv("E:/OneDrive/grace-web/grace-data/group/foreign/sh/foreign-sh-20210105.xlsx");
    }

    @Test
    public void test_writeAllForeignDataToCsv() throws IOException, ParseException {
        ForeignDataSource.writeAllForeignDataToCsv();
    }

    @Test
    public void test_loadCsv() throws IOException, ParseException {

        List<String> results = new ArrayList<>();
        results = ForeignDataSource.getForeignCsvFileList();
        for (String file:results){
            System.out.println(file);
            BarSeries barSeries = ForeignDataSource.loadCsv(file);
            System.out.println(barSeries.getBarCount());
        }




    }
}
