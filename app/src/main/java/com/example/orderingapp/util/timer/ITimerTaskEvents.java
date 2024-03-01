package com.example.orderingapp.util.timer;

import com.example.orderingapp.model.FinalizedOrder;

public interface ITimerTaskEvents {

    void onTimerTaskEvent(FinalizedOrder finalizedOrder);
}
