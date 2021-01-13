package com.example.jdspider.service;

import com.example.jdspider.common.constants.ActionType;

/**
 * @author yifanwang
 */
public interface RefreshStatusService {

    void offer(String email, ActionType actionType);

}
