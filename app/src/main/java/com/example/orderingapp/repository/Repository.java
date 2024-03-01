package com.example.orderingapp.repository;

import com.example.orderingapp.util.callbacks.OnItemsReceivedCallback;
import com.example.orderingapp.util.callbacks.OnOrderReceivedCallback;
import com.example.orderingapp.util.callbacks.OnStatusReceivedCallback;
import com.example.orderingapp.DAO.PostRequest;

public interface Repository {
    //GET RUTA ZA SVE NAPITKE
    void getRestaurantMenuItems(Long tableId, OnItemsReceivedCallback callback);

    //SLANJE NARUDZBe
    void makeOrder(PostRequest postRequest, OnOrderReceivedCallback onOrderReceivedCallback);
    void orderUpdated(Long tableId, OnStatusReceivedCallback onStatusReceivedCallback);
}
