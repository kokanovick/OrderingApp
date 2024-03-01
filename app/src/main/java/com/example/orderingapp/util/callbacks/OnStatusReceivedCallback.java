package com.example.orderingapp.util.callbacks;

import com.example.orderingapp.model.FinalizedOrder;

public interface OnStatusReceivedCallback {
    void onStatusReceived(FinalizedOrder finalizedOrder);
}
