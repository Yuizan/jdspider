package com.example.jdspider.common.annotations;

import com.example.jdspider.common.constants.ActionType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yifanwang
 */
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

    ActionType type();
}
