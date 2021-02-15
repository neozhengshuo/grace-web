package com.zhs.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

public class FileUtilTest {
    @Test
    public void getStockFilesWithFullPathTest(){
        List<String> fileList = FileUtil.getStockFilesWithFullPath();
        fileList.forEach(System.out::println);
    }
}
