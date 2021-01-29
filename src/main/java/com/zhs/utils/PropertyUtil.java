package com.zhs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties properties;

    static {
        loadProperties();
    }

    synchronized static private void loadProperties(){
        properties = new Properties();
        InputStream in = null;
        try{
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
            properties.load(in);
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                logger.error(e.getMessage());
            }
        }
    }

    public static String getProperty(String key,String defaultValue){
        if(properties == null){
            loadProperties();
        }
        return properties.getProperty(key,defaultValue);
    }

    public static String getProperty(String key){
        if(properties == null){
            loadProperties();
        }
        return properties.getProperty(key);
    }

}
