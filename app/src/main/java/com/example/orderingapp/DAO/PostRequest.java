package com.example.orderingapp.DAO;

import com.example.orderingapp.model.OrderRequest;

import java.util.List;

public class PostRequest {

    public Long tableId;

    public String comment;

    public String status = "NEW";

    public List<OrderRequest> orderedDrinks;

    public PostRequest() {

    }

    public PostRequest(Long tableId, List<OrderRequest> orderedDrinks) {
        this.tableId = tableId;
        this.orderedDrinks = orderedDrinks;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<OrderRequest> getOrderedDrinks() {
        return orderedDrinks;
    }

    public void setOrderedDrinks(List<OrderRequest> orderRequestList) {
        this.orderedDrinks = orderRequestList;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "tableId=" + tableId +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", orderedDrinks=" + orderedDrinks +
                '}';
    }
}
