package com.example.orderingapp.DAO;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.orderingapp.util.callbacks.OnItemsReceivedCallback;
import com.example.orderingapp.util.callbacks.OnOrderReceivedCallback;
import com.example.orderingapp.util.callbacks.OnStatusReceivedCallback;
import com.example.orderingapp.model.FinalizedOrder;
import com.example.orderingapp.model.OrderResponse;
import com.example.orderingapp.model.TableAvailableDrinks;
import com.example.orderingapp.repository.Repository;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RealRepository implements Repository {
    public void getRestaurantMenuItems(Long tableId, OnItemsReceivedCallback receivedCallback) {
        JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
        Call<TableAvailableDrinks> call = jsonPlaceHolderAPI.getRestaurantMenuItems(tableId);
        call.enqueue(new Callback<TableAvailableDrinks>() {
            @Override
            public void onResponse(@NonNull Call<TableAvailableDrinks> call, @NonNull Response<TableAvailableDrinks> response) {
                if (!response.isSuccessful()) {
                    Log.d("Unsuccessful response getItems", response.message());
                } else {
                    Log.d("Response Code: ", String.valueOf(response.code()));
                    Log.d("Response Body: ", new Gson().toJson(response.body()));
                }
                receivedCallback.onItemsReceived(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TableAvailableDrinks> call, @NonNull Throwable t) {
                call.cancel();
                Log.d("Failed response getItems", t.getMessage());
            }
        });
    }
    @NonNull
    private static JsonPlaceHolderAPI getJsonPlaceHolderAPI() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.15.207.161:8080").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(JsonPlaceHolderAPI.class);
    }

    @Override
    public void makeOrder(PostRequest postRequest, OnOrderReceivedCallback onOrderReceivedCallback) {
        JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
        Call<OrderResponse> call = jsonPlaceHolderAPI.createRestaurantMenuItem(postRequest);
        call.enqueue(new Callback<OrderResponse> () {
                @Override
                public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse>  response) {
                    if(!response.isSuccessful()){
                        Log.d("Unsuccessful response makeOrder", response.message());
                        return;
                    }
                    onOrderReceivedCallback.onOrderReceived(response.body());
                }
                @Override
                public void onFailure(@NonNull Call<OrderResponse>  call, @NonNull Throwable t) {
                    Log.d("Failed makeOrder", t.getMessage());
                }
            });
        }

    @Override
    public void orderUpdated(Long tableId, OnStatusReceivedCallback onStatusReceivedCallback) {
        JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
        Call<FinalizedOrder> call = jsonPlaceHolderAPI.getOrderStatus(tableId);
        call.enqueue(new Callback<FinalizedOrder>() {
            @Override
            public void onResponse(Call<FinalizedOrder> call, Response<FinalizedOrder> response) {
                if(!response.isSuccessful()){
                    Log.d("Unsuccessful response orderUpdated", response.message());
                    return;
                }
                onStatusReceivedCallback.onStatusReceived(response.body());
            }

            @Override
            public void onFailure(Call<FinalizedOrder> call, Throwable t) {
                Log.d("Failed orderUpdated", t.getMessage());
            }
        });
    }
}
