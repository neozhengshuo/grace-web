package com.zhs.foreign;

import com.zhs.datasource.ForeignExcelDataSource;
import com.zhs.entities.Foreign;
import com.zhs.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ForeignExcelDataSourceTest {
    @Test
    public void test_getExcelFileList(){
        List<String>  fileList = ForeignExcelDataSource.getExcelFileList();
        System.out.println(fileList.size());
        for (String file:fileList){
            System.out.println(file);
        }
    }

    @Test
    public void test_ReadExcel() throws IOException {
        List<Foreign> foreignList = FileUtil.readForeignExcel("E:/OneDrive/grace-web/grace-data/group/foreign/sh/foreign-sh-20210201.xlsx");
        System.out.println(foreignList.size());
        for (Foreign foreign : foreignList) {
            System.out.println(foreign);
        }
    }

    @Test
    public void test_writeSingleForeignDataToCsv() throws IOException, ParseException {
        ForeignExcelDataSource.writeSingleForeignDataToCsv("E:/OneDrive/grace-web/grace-data/group/foreign/sh/foreign-sh-20210201.xlsx");
    }

    @Test
    public void test_writeAllForeignDataToCsv() throws IOException, ParseException {
        ForeignExcelDataSource.writeAllForeignDataToCsv();
    }

}
