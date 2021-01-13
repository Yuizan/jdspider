package com.example.jdspider.common.constants;

import com.example.jdspider.common.browser.BrowserDriverFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yifanwang
 */

public class UserInfo {

    private static final Map<String, UserContext> USERINFO = new ConcurrentHashMap<>();
    private static final ThreadLocal<UserContext> context = new ThreadLocal<>();

    public static UserContext getUserInfo(String email) {
        return USERINFO.get(email);
    }

    public static Map<String, UserContext> initUserInfo(String email) {
        UserContext userContext = new UserContext();
        userContext.email = email;
        userContext.driver = BrowserDriverFactory.get(BrowserType.CHROME);
        USERINFO.put(email, userContext);
        return USERINFO;
    }


    public static void setContext(String email) {
        context.set(USERINFO.get(email));
    }

    public static UserContext getContext() {
        return context.get();
    }

    public static void removeContext() {
        context.remove();
    }


    public static class UserContext {
        public Boolean isLogin;
        public Set<Cookie> cookie;
        public String email;
        public RemoteWebDriver driver;

    }
}
