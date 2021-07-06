package com.zhs.foreign;

import com.zhs.datasource.ForeignDataSource;
import com.zhs.entities.Foreign;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BarSeries;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForeignDataSourceTest {
    @Test
    public void test_getExcelFileList(){
        String source = PropertyUtil.getProperty("foreign-daily-data");
        List<String>  fileList = ForeignDataSource.getExcelFileList(source);
        System.out.println(fileList.size());
        for (String file:fileList){
            System.out.println(file);
        }
    }

    @Test
    public void test_ReadExcel() throws IOException {
        List<Foreign> foreignList = FileUtil.readForeignExcel("E:/OneDrive/grace-web/grace-data/group/foreign/sz/foreign-sz-20210705.xlsx");
        System.out.println(foreignList.size());
        for (Foreign foreign : foreignList) {
            System.out.println(foreign);
        }
    }

    @Test
    public void test_readAllForeignExcel() throws IOException {
        String sourceDir_SH = PropertyUtil.getProperty("foreign-daily-data-SH");
        String sourceDir_SZ = PropertyUtil.getProperty("foreign-daily-data-SZ");
        List<String>  fileList = ForeignDataSource.getExcelFileList(sourceDir_SZ);
        System.out.println(fileList.size());

        for (String file:fileList){
            List<Foreign> foreignList = FileUtil.readForeignExcel(file);
            System.out.println(file + " " +foreignList.size());
        }
    }

    @Test
    public void test_writeSingleForeignDataToCsv() throws IOException, ParseException {
        String sourceDir = PropertyUtil.getProperty("foreign-daily-data-SH");
        String outputDir = PropertyUtil.getProperty("foreign-processed-output");
        ForeignDataSource.writeSingleForeignDataToCsv(outputDir,"E:/OneDrive/grace-web/grace-data/group/foreign/sz/foreign-sz-20210705.xlsx");
    }

    @Test
    public void test_writeAllForeignDataToCsv_SH() throws IOException, ParseException {
        String sourceDir = PropertyUtil.getProperty("foreign-daily-data-SH");
        String outputDir = PropertyUtil.getProperty("foreign-processed-output");
        ForeignDataSource.writeAllForeignDataToCsv(sourceDir,outputDir);
    }

    @Test
    public void test_writeAllForeignDataToCsv_SZ() throws IOException, ParseException {
        String sourceDir = PropertyUtil.getProperty("foreign-daily-data-SZ");
        String outputDir = PropertyUtil.getProperty("foreign-processed-output");
        ForeignDataSource.writeAllForeignDataToCsv(sourceDir,outputDir);
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
