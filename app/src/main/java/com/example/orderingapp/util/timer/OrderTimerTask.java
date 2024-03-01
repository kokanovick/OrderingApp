package com.example.orderingapp.util.timer;

import com.example.orderingapp.util.callbacks.OnStatusReceivedCallback;
import com.example.orderingapp.model.FinalizedOrder;
import com.example.orderingapp.model.OrderResponse;
import com.example.orderingapp.DAO.RealRepository;
import com.example.orderingapp.repository.Repository;

import java.util.TimerTask;

public class OrderTimerTask extends TimerTask {

    private final OrderResponse orderResponse;

    private ITimerTaskEvents timerTaskEvents;

    public OrderTimerTask(OrderResponse orderResponse){
        this.orderResponse = orderResponse;
    }
    @Override
    public void run() {
        receiveStatus();
    }

    public void setTimerTaskEventsListener(ITimerTaskEvents timerTaskEvents){
        this.timerTaskEvents = timerTaskEvents;
    }

    public void receiveStatus() {
        Repository repository = new RealRepository();
        if (orderResponse != null) {
            repository.orderUpdated((long) orderResponse.getOrder_id(), new OnStatusReceivedCallback() {
                @Override
                public void onStatusReceived(FinalizedOrder finalizedOrder) {
                    if(timerTaskEvents != null){
                        timerTaskEvents.onTimerTaskEvent(finalizedOrder);
                    }
                }
            });
        }
    }
}
