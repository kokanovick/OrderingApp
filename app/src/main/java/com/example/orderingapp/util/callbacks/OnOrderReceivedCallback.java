package com.example.orderingapp.util.callbacks;

import com.example.orderingapp.model.OrderResponse;

public interface OnOrderReceivedCallback {
    void onOrderReceived(OrderResponse orderResponse);
}
