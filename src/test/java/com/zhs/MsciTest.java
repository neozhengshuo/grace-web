package com.zhs;

import com.zhs.analysis.TrendAnalyzer;
import com.zhs.utils.FileUtil;
import com.zhs.utils.PropertyUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MsciTest {
    private static final String msciTxt;

    static {
        msciTxt = PropertyUtil.getProperty("msci-output")+"/msci-20210305-140405.txt";
    }

    /**
     * 获得Msci中股票的编码
     * @return 编码
     */
    private List<String> getMsciCode(){
        List<String> msciCodes = new ArrayList<>();

        File file = new File(msciTxt);
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String str = null;
            while((str = br.readLine())!=null){
                msciCodes.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msciCodes;
    }

    /**
     * 通过code找到股票的行情文件
     * @param code  股票代码
     * @return 行情文件路径
     */
    private String findStockFile(String code){
        List<String> stockFiles = FileUtil.getStockFilesWithFullPath();
        for (String file :
                stockFiles) {
            int index = file.indexOf(code);
            if(index>=0){
                return file;
            }
        }
        return null;
    }


    /**
     * 获取Msci中股票的交易数据文件
     * @return 交易数据文件路径
     */
    private List<String> getMsciStockTradingFile(){
        List<String> msciStockCode = this.getMsciCode();

        List<String> files = new ArrayList<>();
        for (String code:msciStockCode){
            String stockFile = this.findStockFile(code);
            if(stockFile!=null){
                files.add(stockFile);
            }
        }
        return files;
    }

    /**
     * 在msci中查找趋势向上的股票
     */
    @Test
    public void trendsUpInMSCI(){
        List<String> stockFiles = this.getMsciStockTradingFile();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(stockFiles);
        List<String> results = trendAnalyzer.loadTrendUp();

        FileUtil.writeTxtFile("趋势向上",results,true);
        results.forEach(System.out::println);
        System.out.printf("趋势向上: %s%n",results.size());
    }

    /**
     * 在msci中查找趋势向上的股票，且J值在低档上涨
     */
    @Test
    public void trendsUpAndJUpInMSCI(){
        List<String> stockFiles = this.getMsciStockTradingFile();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(stockFiles);
        List<String> results = trendAnalyzer.getJLow(50);

        FileUtil.writeTxtFile("趋势向上-J值上涨",results,true);
        results.forEach(System.out::println);
        System.out.printf("getKDJ: %s%n",results.size());
    }

    /**
     * 在msci中查找趋势向上的股票，且股价在布林中轨以下。
     */
    @Test
    public void trendsUpAndBollMidInMSCI(){
        List<String> stockFiles = this.getMsciStockTradingFile();
        TrendAnalyzer trendAnalyzer = new TrendAnalyzer(stockFiles);
        List<String> results = trendAnalyzer.getTrendUpBollMid();

        FileUtil.writeTxtFile("趋势向上-价在布林中轨以下",results,true);
        results.forEach(System.out::println);
        System.out.printf("boll mid: %s%n",results.size());
    }
}
