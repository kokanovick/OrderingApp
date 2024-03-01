package com.example.orderingapp.model;

import java.util.List;

public class TableAvailableDrinks {
    private Long tableId;
    private List<RestaurantMenuItem> drinks;

    public TableAvailableDrinks(Long tableId, List<RestaurantMenuItem> drinks) {
        this.tableId = tableId;
        this.drinks = drinks;
    }

    public TableAvailableDrinks(){

    }
    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<RestaurantMenuItem> getRestaurantMenuItemList() {
        return drinks;
    }

    public void setRestaurantMenuItemList(List<RestaurantMenuItem> drinks) {
        this.drinks = drinks;
    }

    @Override
    public String toString() {
        return "TableAvailableDrinks{" +
                "tableId=" + tableId +
                ", restaurantMenuItemList=" + drinks +
                '}';
    }
}
