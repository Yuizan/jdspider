package com.example.jdspider.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yifanwang
 */
public enum ActionType {

    /**
     * LOGIN 登陆
     */
    LOGIN("login", "登陆"),
    PREORDER("PREORDER", "预定"),
    CHECKLOGIN("CHECKLOGIN", "查看登陆"),
    PURCHASE("PURCHASE", "抢购")
    ;

    private String type;
    private String name;

    private final static Map<String, ActionType> VALUES = new HashMap<>();

    static {
        for(ActionType browserType:values()){
            VALUES.put(browserType.type, browserType);
        }
    }
    ActionType(String type, String name){
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

    public static ActionType getBrowserTypeEnum(String code){
        return VALUES.get(code);
    }
}
