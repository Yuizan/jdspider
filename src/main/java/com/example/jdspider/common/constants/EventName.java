package com.example.jdspider.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yifanwang
 */
public enum EventName {

    /**
     * LOGIN 登陆
     */
    INITACTIONS("INITACTIONS", "初始化动作"),
    INITBROWSERS("INITBROWSERS", "初始化浏览器"),
    ;
    private String type;
    private String name;

    private final static Map<String, EventName> VALUES = new HashMap<>();

    static {
        for(EventName browserType:values()){
            VALUES.put(browserType.type, browserType);
        }
    }
    EventName(String type, String name){
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

    public static EventName getBrowserTypeEnum(String code){
        return VALUES.get(code);
    }

    public static Map<String, EventName> getValues(){
        return VALUES;
    }

}
