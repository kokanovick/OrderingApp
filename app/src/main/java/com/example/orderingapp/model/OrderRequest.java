package com.example.orderingapp.model;

public class OrderRequest {

    private Long drinkId;
    private int quantity = 0;
    private String name;
    private double price;

    public OrderRequest() {
    }

    public OrderRequest(Long drinkId, int quantity) {
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setDrinkId(Long drinkId) {
        this.drinkId = drinkId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
