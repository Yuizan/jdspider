package com.example.jdspider.config;

import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.common.annotations.Browser;
import com.example.jdspider.common.browser.BrowserDriverFactory;
import com.example.jdspider.common.browser.WebDriverStrategy;
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
public class BrowsersAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(BrowsersAutoConfiguration.class);

    private final ApplicationContext publisher;

    public BrowsersAutoConfiguration(ApplicationContext publisher){
        this.publisher = publisher;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, Object> beanMap = context.getBeansWithAnnotation(Browser.class);
        LOGGER.info("获取到全部浏览器 [{}]", beanMap.keySet());
        for (Object bean : beanMap.values()) {
            Browser browser = AnnotationUtils.findAnnotation(bean.getClass(), Browser.class);
            LOGGER.info("加载浏览器 [{}]", bean);
            Assert.notNull(browser, "浏览器驱动不能为空");
            BrowserDriverFactory.put(browser.type(), (WebDriverStrategy) bean);
        }
        publisher.publishEvent(new InitEvent(this, EventName.INITBROWSERS));
    }
}
