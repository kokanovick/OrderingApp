package com.example.orderingapp.DAO;

import com.example.orderingapp.model.FinalizedOrder;
import com.example.orderingapp.model.OrderResponse;
import com.example.orderingapp.model.TableAvailableDrinks;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {

    @GET("drink/getAll/{tableNumber}")
    Call<TableAvailableDrinks> getRestaurantMenuItems(@Path("tableNumber")Long tableNumber);
    @POST("myorder")
    Call<OrderResponse> createRestaurantMenuItem(@Body PostRequest postRequest);
    @GET("myorder/{orderId}")
    Call<FinalizedOrder> getOrderStatus(@Path("orderId")Long orderId);
}
