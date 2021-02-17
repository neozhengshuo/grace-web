package com.zhs.utils;

import com.zhs.entities.Foreign;
import com.zhs.utils.os.OsInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class FileUtilTest {
    private static final String macos = "Users/zhengshuo/Desktop/foreign-sh.xlsx";
    private static final String winos = "C:\\Users\\neozheng\\Desktop\\foreign-sh.xlsx";

    @Test
    public void getStockFilesWithFullPathTest(){
        List<String> fileList = FileUtil.getStockFilesWithFullPath();
        fileList.forEach(System.out::println);
    }

    @Test
    public void readForeignExcelTest(){
        List<Foreign> foreignList = null;
        try {
            if(OsInfo.isWindows()){
                foreignList =
                        FileUtil.readForeignExcel(winos);
            }else if(OsInfo.isMacOSX()){
                foreignList =
                        FileUtil.readForeignExcel(macos);
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
