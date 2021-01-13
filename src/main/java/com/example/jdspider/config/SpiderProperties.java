package com.example.jdspider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yifanwang
 */
@Component
@ConfigurationProperties(prefix = "spider")
public class SpiderProperties {

    private static String[] email;
    private static Integer count;
    private static String goodId;
    private static String imgDir;
    private static String driver;

    public static String[] getEmail() {
        return email;
    }

    public static Integer getCount() {
        return count;
    }

    public void setEmail(String[] email) {
        SpiderProperties.email = email;
    }

    public void setCount(Integer count) {
        SpiderProperties.count = count;
    }

    public static String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        SpiderProperties.goodId = goodId;
    }

    public static String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        SpiderProperties.imgDir = imgDir;
    }

    public static String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        SpiderProperties.driver = driver;
    }
}
