package com.example.jdspider.model;

import com.example.jdspider.common.constants.ActionType;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author yifanwang
 * @Description: Message消息对象
 */
public class ActionDelayMessage implements Delayed {

    private long time;
    private String email;
    private ActionType actionType;

    public ActionDelayMessage(String email, ActionType actionType, long time, TimeUnit unit) {
        this.email = email;
        this.actionType = actionType;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        ActionDelayMessage item = (ActionDelayMessage) o;
        long diff = this.time - item.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "UnionQueryMessage{" +
                "time=" + time +
                ", email='" + email + '\'' +
                ", actionType=" + actionType +
                '}';
    }
}
