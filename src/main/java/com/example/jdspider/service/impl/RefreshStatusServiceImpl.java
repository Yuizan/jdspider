package com.example.jdspider.service.impl;

import com.example.jdspider.actions.ActionFactory;
import com.example.jdspider.actions.ActionReactor;
import com.example.jdspider.common.constants.ActionType;
import com.example.jdspider.model.ActionDelayMessage;
import com.example.jdspider.service.RefreshStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @author yifanwang
 */
@Service
public class RefreshStatusServiceImpl implements RefreshStatusService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ActionReactor.class);

    private final DelayQueue<ActionDelayMessage> delayQueue;

    public RefreshStatusServiceImpl(){
        this.delayQueue = new DelayQueue<>();
        ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), r -> {
            Thread thread = new Thread(r);
            thread.setName(r.getClass().getName());
            return thread;
        });

        executorService.execute(()->{
            while (true) {
                try {
                    ActionDelayMessage message = delayQueue.take();
                    Future<Boolean> future = ActionReactor.dispatch(message.getEmail(), message.getActionType());
                    if(future.get()){
                        this.offer(message.getEmail(), message.getActionType());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void offer(String email, ActionType actionType){
        int waitMinutes = (int)(10+Math.random()*(20-10+1));
        LocalDateTime nextOperationTime = LocalDateTime.now().plusMinutes(waitMinutes);

        LocalDateTime minPurchasePeriod = LocalDateTime.of(nextOperationTime.getYear(), nextOperationTime.getMonth(), nextOperationTime.getDayOfMonth(), 9, 50, 0 );
        LocalDateTime maxPurchasePeriod = LocalDateTime.of(nextOperationTime.getYear(), nextOperationTime.getMonth(), nextOperationTime.getDayOfMonth(), 10, 10, 0 );
        LocalDateTime minPreOrderPeriod = LocalDateTime.of(nextOperationTime.getYear(), nextOperationTime.getMonth(), nextOperationTime.getDayOfMonth(), 23, 10, 0 );
        LocalDateTime maxPreOrderPeriod = LocalDateTime.of(nextOperationTime.getYear(), nextOperationTime.getMonth(), nextOperationTime.getDayOfMonth(), 23, 20, 0 );

        if(nextOperationTime.isAfter(minPurchasePeriod) && nextOperationTime.isBefore(maxPurchasePeriod)){
            waitMinutes = 20;
        }
        if(nextOperationTime.isAfter(minPreOrderPeriod) && nextOperationTime.isBefore(maxPreOrderPeriod)){
            waitMinutes = 20;
        }

        LOGGER.info("[{}]下次刷新, [{}] 分钟后", email, waitMinutes);
        ActionDelayMessage message = new ActionDelayMessage(email, actionType, waitMinutes, TimeUnit.MINUTES);

        delayQueue.offer(message);
    }
}
