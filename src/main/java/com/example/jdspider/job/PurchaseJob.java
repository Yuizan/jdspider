package com.example.jdspider.job;

import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.config.SpiderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yifanwang
 */
@Component
public class PurchaseJob {

    @Scheduled(cron = "${cron.purchase}")
    public void preOrderJob(){
        for(String email: SpiderProperties.getEmail()){
            ActionReactor.dispatch(email, ActionType.PURCHASE);
        }
    }
}
