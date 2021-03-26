package com.zhs.datasource;

import com.zhs.datasource.msci.MsciExcelHandler;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MsciExcelTest {
    /**
     * 从指定目录中获取msci股票名单，并输出到指定目录的文本文件中
     * @throws IOException 异常
     */
    @Test
    public void produceMsciStock() throws IOException {
        String sourceDir = PropertyUtil.getProperty("msci-source");
        String outputDir = PropertyUtil.getProperty("msci-output");

        List<String[]> codeAndName = MsciExcelHandler.getMsciStock(sourceDir);
        String txtFile = MsciExcelHandler.writeMsciStockToTxtFile(outputDir,codeAndName);
        FileUtil.openTxt(txtFile);

        for (String[] item:codeAndName){
            System.out.println("code:"+item[0]+ " name:"+item[1]);
        }
        System.out.println(txtFile);
        System.out.println(codeAndName.size());
    }
}
