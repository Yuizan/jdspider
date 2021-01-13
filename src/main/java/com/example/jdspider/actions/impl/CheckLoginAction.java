package com.example.jdspider.actions.impl;

import com.example.jdspider.actions.SpiderAction;
import com.example.jdspider.common.annotations.Action;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.UserInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author yifanwang
 */
@Action(type = ActionType.CHECKLOGIN)
public class CheckLoginAction implements SpiderAction {

    @Override
    public boolean execute(RemoteWebDriver driver){
        try{
            driver.get("https://www.jd.com");
            driver.navigate().refresh();
            driver.findElement(By.className("nickname"));
            return true;
        }catch (Exception e){
            UserInfo.getContext().isLogin = false;
            return false;
        }finally {
        }

    }

}
