package com.example.jdspider.actions;

import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.UserInfo;
import com.example.jdspider.job.PurchaseJob;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author yifanwang
 */
@Component
public class ActionReactor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ActionReactor.class);

    private static ExecutorService executorService;

    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(8, 16, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static Future<Boolean> dispatch(String email, ActionType actionType){
        return executorService.submit(()->{
            LOGGER.info("开始操作 [{}] 操作名 [{}]", email, actionType);
            UserInfo.setContext(email);
            try {
                SpiderAction spiderAction = ActionFactory.get(actionType);
                if(spiderAction == null){
                    LOGGER.info("未获取到操作 [{}]", actionType);
                    return null;
                }
                return ActionFactory.get(actionType).execute(UserInfo.getContext().driver);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }finally {
                LOGGER.info("操作结束 [{}] 操作名 [{}]", email, actionType);
                UserInfo.removeContext();
            }
        });
    }

}
