package com.example.orderingapp.model;

public class OrderResponse {
    private int myOrderId;
    private String status;

    public OrderResponse(){

    }
    public OrderResponse(int order_id, String orderStatus) {
        this.myOrderId = order_id;
        this.status = orderStatus;
    }

    public int getOrder_id() {
        return myOrderId;
    }

    public String getOrderStatus() {
        return status;
    }

    public void setOrder_id(int order_id) {
        this.myOrderId = order_id;
    }

    public void setOrderStatus(String orderStatus) {
        this.status = orderStatus;
    }
}
