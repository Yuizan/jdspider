package com.example.jdspider.event;

import com.example.jdspider.common.constants.EventName;
import org.springframework.context.ApplicationEvent;

/**
 * @author yifanwang
 */
public class InitEvent extends ApplicationEvent {

    private EventName eventName;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public InitEvent(Object source, EventName eventName) {
        super(source);
        this.eventName = eventName;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        this.eventName = eventName;
    }
}
