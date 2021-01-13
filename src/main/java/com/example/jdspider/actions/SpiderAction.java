package com.example.jdspider.actions;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author yifanwang
 */
public interface SpiderAction {
    boolean execute(RemoteWebDriver driver) throws Exception;
}
