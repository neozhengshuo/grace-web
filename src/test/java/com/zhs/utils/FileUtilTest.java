package com.zhs.utils;

import com.zhs.entities.Foreign;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class FileUtilTest {
    @Test
    public void getStockFilesWithFullPathTest(){
        List<String> fileList = FileUtil.getStockFilesWithFullPath();
        fileList.forEach(System.out::println);
    }

    @Test
    public void readForeignExcelTest(){
        // /Users/zhengshuo/Desktop/foreign-sh.xlsx
        try {
            List<Foreign> foreignList =
                    FileUtil.readForeignExcel("/Users/zhengshuo/Desktop/foreign-sh.xlsx");
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
