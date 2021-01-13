package com.example.jdspider.config;

import com.example.jdspider.actions.ActionProxy;
import com.example.jdspider.actions.ActionFactory;
import com.example.jdspider.actions.SpiderAction;
import com.example.jdspider.common.annotations.Action;
import com.example.jdspider.common.constants.EventName;
import com.example.jdspider.event.InitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author yifanwang
 */
@Configuration
public class ActionsAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ActionsAutoConfiguration.class);

    private final ApplicationContext publisher;

    public ActionsAutoConfiguration(ApplicationContext publisher){
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, Object> beanMap = context.getBeansWithAnnotation(Action.class);
        LOGGER.info("获取到全部行为 [{}]", beanMap.keySet());
        for (Object bean : beanMap.values()) {
            Action action = AnnotationUtils.findAnnotation(bean.getClass(), Action.class);
            LOGGER.info("加载行为 [{}]", bean);
            Assert.notNull(action, "行为不能为空");
            ActionProxy proxy = new ActionProxy();
            SpiderAction spiderAction = (SpiderAction) bean;
            ActionFactory.put(action.type(), (SpiderAction) proxy.getInstance(spiderAction));
        }
        publisher.publishEvent(new InitEvent(this, EventName.INITACTIONS));
    }
}
