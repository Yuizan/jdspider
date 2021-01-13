package com.example.jdspider.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yifanwang
 */
public enum BrowserType {

    /**
     * chrome 谷歌浏览器
     */
    CHROME("chrome", "谷歌浏览器");

    private String type;
    private String name;

    private final static Map<String, BrowserType> VALUES = new HashMap<>();

    static {
        for(BrowserType browserType:values()){
            VALUES.put(browserType.type, browserType);
        }
    }
    BrowserType(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BrowserType getBrowserTypeEnum(String code){
        return VALUES.get(code);
    }
}
