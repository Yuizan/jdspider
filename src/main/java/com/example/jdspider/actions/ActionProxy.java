package com.example.jdspider.actions;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yifanwang
 */
public class ActionProxy implements InvocationHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ActionProxy.class);

    private Object target; // 代理对象

    public ActionProxy(){ }
    public Object getInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            result = method.invoke(target, args); // 方法调用
        }catch (Exception e){
            LOGGER.info("异常", e);
        }
        return result;
    }
}
