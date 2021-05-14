package com.zhs;

import com.zhs.datasource.FileStockDailyData;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
    private String getDayOfWeek(Date date){
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int dayOfWeek = -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0)
            week = 0;
        return weekDays[week];
    }

    private String getDayOfWeek(String date) throws ParseException {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = dateFormat.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0)
            week = 0;
        return weekDays[week];
    }

    public int getWeek(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = dateFormat.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(targetDate);
        return calendar.get(Calendar.WEEK_OF_YEAR) - 2;
    }

    @Test
    public void test() throws ParseException {
        BaseBarSeries barSeries = FileStockDailyData.load("D:/zd_zsone/T0002/export/SH#600016.txt");
        int endIndex = barSeries.getEndIndex();
        for (int i = 0;i<=endIndex;i++){
            String date = barSeries.getBar(i).getDateName();
            String dayOfWeek = this.getDayOfWeek(date);
            int week = this.getWeek(date);
        }
    }
}
