package com.example.jdspider.actions.impl;

import com.example.jdspider.actions.SpiderAction;
import com.example.jdspider.common.annotations.Action;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.UserInfo;
import com.example.jdspider.config.SpiderProperties;
import com.example.jdspider.service.IMailService;
import com.example.jdspider.service.RefreshStatusService;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author yifanwang
 */
@Action(type = ActionType.LOGIN)
public class LoginAction implements SpiderAction {

    private final RefreshStatusService refreshStatusService;
    private final IMailService iMailService;

    public LoginAction(RefreshStatusService refreshStatusService, IMailService iMailService){
        this.refreshStatusService = refreshStatusService;
        this.iMailService = iMailService;
    }
    @Override
    public boolean execute(RemoteWebDriver driver) throws Exception {
        driver.get("https://www.jd.com/");
        WebElement username = driver.findElement(By.className("link-login"));
        username.click();
        byte[] body = driver.getScreenshotAs(OutputType.BYTES);
        FileOutputStream fileOutputStream = null;
        String filePath = SpiderProperties.getImgDir() + "/" + UserInfo.getContext().email + "-login.png";
        try {
            fileOutputStream = new FileOutputStream(new File(filePath));
            fileOutputStream.write(body);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iMailService.sendAttachmentsMail(UserInfo.getContext().email, "登陆图片", "登陆图片", filePath);
        boolean isLogin = false;
        while(!isLogin){
            if(!driver.getCurrentUrl().contains("passport.jd.com")){
                WebElement nickname = driver.findElement(By.className("nickname"));
                if(StringUtils.isNotBlank(nickname.getText())){
                    isLogin = true;
                }
            }
            Thread.sleep(1000);
        }

        UserInfo.getContext().cookie = driver.manage().getCookies().stream().filter((o)-> o.getDomain().contains(".jd.com")).collect(Collectors.toSet());
        UserInfo.getContext().isLogin = true;
        refreshStatusService.offer(UserInfo.getContext().email, ActionType.CHECKLOGIN);
        return true;

    }

}
