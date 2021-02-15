package com.zhs.utils;

import com.zhs.utils.os.OsInfo;
import org.junit.jupiter.api.Test;

public class OsInfoTest {
    @Test
    public void osInfoTest(){
        System.out.println(OsInfo.getOSname());
        System.out.println(OsInfo.isMacOSX());
    }
}
