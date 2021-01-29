package com.zhs.utils;

import org.junit.jupiter.api.Test;

public class PropertyUtilTest {
    @Test
    void loadProperties(){
        String value = PropertyUtil.getProperty("stock-daily-data");
        System.out.println(value);
    }
}
