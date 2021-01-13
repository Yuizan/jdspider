package com.example.jdspider.controller;

import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.common.constants.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author yifanwang
 */
@RestController
@RequestMapping("/v1/action")
public class ActionController {

    @RequestMapping("/login/{email}")
    public String login(@PathVariable String email) {
        UserInfo.UserContext userContext = UserInfo.getUserInfo(email);
        if(userContext == null){
            return "userContext is not exist";
        }
        ActionReactor.dispatch(email, ActionType.LOGIN);
        return "success";
    }

    @RequestMapping("/preOrder/{email}")
    public String preOrder(@PathVariable String email) {
        UserInfo.UserContext userContext = UserInfo.getUserInfo(email);
        if(userContext == null){
            return "userContext is not exist";
        }
        ActionReactor.dispatch(email, ActionType.PREORDER);
        return "success";
    }

    @RequestMapping("/purchase/{email}")
    public String purchase(@PathVariable String email) {
        UserInfo.UserContext userContext = UserInfo.getUserInfo(email);
        if(userContext == null){
            return "userContext is not exist";
        }
        ActionReactor.dispatch(email, ActionType.PURCHASE);
        return "success";
    }

    @RequestMapping("/checkStatus/{email}")
    public String checkStatus(@PathVariable String email) {
        UserInfo.UserContext userContext = UserInfo.getUserInfo(email);
        if(userContext == null){
            return "userContext is not exist";
        }
        Future<Boolean> isLogin = ActionReactor.dispatch(email, ActionType.CHECKLOGIN);
        try {
            return String.valueOf(isLogin.get());
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

}
