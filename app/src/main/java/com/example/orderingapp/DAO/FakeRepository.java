package com.example.orderingapp.DAO;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.orderingapp.util.callbacks.OnItemsReceivedCallback;
import com.example.orderingapp.util.callbacks.OnOrderReceivedCallback;
import com.example.orderingapp.util.callbacks.OnStatusReceivedCallback;
import com.example.orderingapp.model.OrderResponse;
import com.example.orderingapp.model.RestaurantMenuItem;
import com.example.orderingapp.model.TableAvailableDrinks;
import com.example.orderingapp.repository.Repository;

import java.util.ArrayList;

public class FakeRepository implements Repository {
    private Context context;

    private OrderResponse orderResponse;

    public FakeRepository(Context context) {
        this.context = context;
    }

    public FakeRepository(){

    }
    private void showToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Dohvacanje ponude (1.korak, GET request s TABLE_ID)
    @Override
    public void getRestaurantMenuItems(Long tableId, OnItemsReceivedCallback itemsReceivedCallback) {
        ArrayList<RestaurantMenuItem> restaurantMenuItemArrayList = new ArrayList<>();
        restaurantMenuItemArrayList.add(new RestaurantMenuItem(0L, "Pivo", 3));
        restaurantMenuItemArrayList.add(new RestaurantMenuItem(1L, "Vino", 3));
        TableAvailableDrinks tableAvailableDrinks = new TableAvailableDrinks();
        tableAvailableDrinks.setRestaurantMenuItemList(restaurantMenuItemArrayList);
        tableAvailableDrinks.setTableId(tableId);
        itemsReceivedCallback.onItemsReceived(tableAvailableDrinks);
    }

   @Override
    public void makeOrder(PostRequest postRequest, OnOrderReceivedCallback onOrderReceivedCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderResponse = new OrderResponse();
                orderResponse.setOrderStatus("NEW");
                orderResponse.setOrder_id(144);
                SystemClock.sleep(2000);
                onOrderReceivedCallback.onOrderReceived(orderResponse);
                showToast("Ready");
            }
        }).start();
    }

    @Override
    public void orderUpdated(Long tableId, OnStatusReceivedCallback onStatusReceivedCallback) {

    }
}
