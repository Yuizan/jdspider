package com.example.jdspider.actions.impl;

import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.actions.SpiderAction;
import com.example.jdspider.common.annotations.Action;
import com.example.jdspider.common.browser.BrowserDriverFactory;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.BrowserType;
import com.example.jdspider.common.constants.UserInfo;
import com.example.jdspider.config.SpiderProperties;
import com.example.jdspider.service.IMailService;
import com.example.jdspider.service.RefreshStatusService;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yifanwang
 */
@Action(type = ActionType.PURCHASE)
public class PurchaseAction implements SpiderAction {

    private ExecutorService executorService;
    private final IMailService iMailService;

    private final static Logger LOGGER = LoggerFactory.getLogger(PurchaseAction.class);

    public PurchaseAction(IMailService iMailService){
        this.iMailService = iMailService;
    }

    @PostConstruct
    public void init(){
        this.executorService = new ThreadPoolExecutor(8, 16, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public boolean execute(RemoteWebDriver driver){
        List<RemoteWebDriver> webDriverList = new ArrayList<>();
        AtomicBoolean isClicked = new AtomicBoolean(false);
        try{
            for(int i = 0; i < SpiderProperties.getCount();i++){
                RemoteWebDriver webDriver = BrowserDriverFactory.get(BrowserType.CHROME);
                webDriverList.add(webDriver);
                webDriver.get("https://item.jd.com/"+SpiderProperties.getGoodId()+".html");
                UserInfo.getContext().cookie.forEach((o)->{
                    webDriver.manage().addCookie(o);
                });
                webDriver.navigate().refresh();
            }

            for(RemoteWebDriver webDriver: webDriverList){
                this.executorService.submit(()->{
                    while (!isClicked.get()){
                        try{
                            webDriver.navigate().refresh();
                            WebElement submitBtn = webDriver.findElement(By.className("btn-special1"));
                            String btnText = submitBtn.getText();
                            if("抢购".equals(btnText)){
                                WebElement btnAdd = webDriver.findElement(By.className("btn-add"));
                                btnAdd.click();
                                submitBtn.click();
                                isClicked.getAndSet(true);
                            }
                        }catch (Exception e){
                            LOGGER.info("异常", e);
                        }
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        LOGGER.info("异常", e);
                    }
                    LOGGER.info("当前页面[{}]", driver.getCurrentUrl());
                    boolean isFail = false;
                    if(driver.getCurrentUrl().contains("marathon.jd.com/koFail.html")){
                        LOGGER.info("抢购失败");
                        isFail = true;
                    }else if(driver.getCurrentUrl().contains("wqs11.jd.com/data/coss/recoverydata")){
                        LOGGER.info("抢购失败");
                        isFail = true;
                    }
                    if(!isFail){
                        LOGGER.info("抢购成功");
                        iMailService.sendSimpleMail(UserInfo.getContext().email, "抢购链接", driver.getCurrentUrl());
                    }

                    webDriver.quit();
                });
            }


            return true;
        }finally {
//            driver.quit();
        }

    }

}
