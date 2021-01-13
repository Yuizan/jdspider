package com.example.jdspider.actions;

import com.example.jdspider.common.constants.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yifanwang
 */
public class ActionFactory {

    private static Map<ActionType, SpiderAction> actionMap = new HashMap<>();

    public static void put(ActionType actionType, SpiderAction strategy){
        actionMap.put(actionType, strategy);
    }

    public static SpiderAction get(ActionType actionType){
        return actionMap.get(actionType);
    }
}
