package com.example.jdspider.common.browser;

import com.example.jdspider.common.annotations.Browser;
import com.example.jdspider.common.constants.BrowserType;
import com.example.jdspider.config.SpiderProperties;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

/**
 * @author yifanwang
 */
@Browser(type = BrowserType.CHROME)
public class Chrome implements WebDriverStrategy {

    @Override
    public RemoteWebDriver getBrowser() {
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(SpiderProperties.getDriver())).build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("disable-infobars","disable-web-security");
        System.setProperty("webdriver.chrome.driver", SpiderProperties.getDriver());
        return new ChromeDriver(service, options);
    }

}
