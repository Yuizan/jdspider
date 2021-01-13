package com.example.jdspider.common.browser;

import com.example.jdspider.common.annotations.Browser;
import com.example.jdspider.common.constants.BrowserType;
import com.example.jdspider.config.SpiderProperties;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yifanwang
 */
public class BrowserDriverFactory {

    private static final Map<BrowserType, Class<? extends WebDriverStrategy>> browsersMap = new HashMap<>();

    public static void put(BrowserType browserType, WebDriverStrategy bean){
        browsersMap.put(browserType, bean.getClass());
    }

    public static RemoteWebDriver get(BrowserType browser){
        Class<? extends WebDriverStrategy> clazz = browsersMap.get(browser);
        try {
            return clazz.newInstance().getBrowser();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
