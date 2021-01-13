package com.example.jdspider.event;

import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.EventName;
import com.example.jdspider.common.constants.UserInfo;
import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.config.ActionsAutoConfiguration;
import com.example.jdspider.config.SpiderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author yifanwang
 */
@Component
public class InitEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitEventListener.class);

    private final Set<EventName> receiveName = new HashSet<>();
    @EventListener
    public void processBlackListEvent(InitEvent event) {
        LOGGER.info("收到事件[{}]", event.getEventName());
        receiveName.add(event.getEventName());

        if(EventName.getValues().keySet().stream().anyMatch(
                (o) -> !receiveName.contains(EventName.getBrowserTypeEnum(o)))
        ){
            LOGGER.info("未满足条件");
            return;
        }
        LOGGER.info("满足条件");
        try {
            for(String email: SpiderProperties.getEmail()){
                UserInfo.initUserInfo(email);
                Future<Boolean> future = ActionReactor.dispatch(email, ActionType.LOGIN);
//                future.get();
//                ActionReactor.dispatch(email, ActionType.PURCHASE);
            }
//            RemoteWebDriver driver = BrowserDriverFactory.get(BrowserType.CHROME);
//            ActionFactory.get(ActionType.LOGIN).execute(driver);
//            ActionFactory.get(ActionType.PURCHASE).execute(driver);
//            ActionFactory.get(ActionType.CHECKLOGIN).execute(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
