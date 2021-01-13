package com.example.jdspider.actions.impl;

import com.example.jdspider.actions.SpiderAction;
import com.example.jdspider.common.annotations.Action;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.config.SpiderProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author yifanwang
 */
@Action(type = ActionType.PREORDER)
public class PreOrderAction implements SpiderAction {

    @Override
    public boolean execute(RemoteWebDriver driver){
        try{
            driver.get("https://item.jd.com/"+ SpiderProperties.getGoodId() + ".html");
            WebElement submitButton = driver.findElement(By.id("btn-reservation"));
            submitButton.click();
            WebElement hintText = driver.findElement(By.className("bd-right-result"));

            return hintText.getText().contains("您已成功预约过了");
        }finally {
//            driver.quit();
        }

    }

}
