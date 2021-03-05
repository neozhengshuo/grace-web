package com.zhs.datasource;

import com.zhs.datasource.msci.MsciExcelHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MsciExcelTest {
    @Test
    public void getMsciStock() throws IOException {
        List<String[]> codeAndName = MsciExcelHandler.getMsciStock("C:\\Users\\neozheng\\Desktop\\MSCI");
        for (String[] item:codeAndName){
            System.out.println("code:"+item[0]+ " name:"+item[1]);
        }
        System.out.println(codeAndName.size());
    }
}
