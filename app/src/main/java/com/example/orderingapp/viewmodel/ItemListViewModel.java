package com.example.orderingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderingapp.util.callbacks.OnItemsReceivedCallback;
import com.example.orderingapp.model.RestaurantMenuItem;
import com.example.orderingapp.model.TableAvailableDrinks;
import com.example.orderingapp.DAO.RealRepository;
import com.example.orderingapp.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListViewModel extends ViewModel {
    private final MutableLiveData<String> itemSum = new MutableLiveData<>("0");
    private Repository repository;
    private Map<Long, Integer> itemQuantityMap = new HashMap<>();
    private MutableLiveData<List<RestaurantMenuItem>> restaurantMenuItemsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<RestaurantMenuItem>> getRestaurantMenuItemsLiveData() {
        return restaurantMenuItemsLiveData;
    }

    public Map<Long, Integer> getItemQuantityMap(){
        return itemQuantityMap;
    }
    public void setQuantity(Long itemId, int quantity) {
        itemQuantityMap.put(itemId, quantity);
    }

    public int getQuantity(Long itemId) {
        return itemQuantityMap.getOrDefault(itemId, 0);
    }
    private Long tableId;

    public ItemListViewModel(Long tableId) {
        this.tableId = tableId;
        repository = new RealRepository();
        fetchMenuItems();
    }

    private void fetchMenuItems() {
        repository.getRestaurantMenuItems(tableId, new OnItemsReceivedCallback() {
            @Override
            public void onItemsReceived(TableAvailableDrinks tableAvailableDrinks) {
                restaurantMenuItemsLiveData.setValue(tableAvailableDrinks.getRestaurantMenuItemList());
            }
        });
    }
    public LiveData<String> getItemSum() {
        return itemSum;
    }

    public void setItemSum(String sum){
        itemSum.postValue(sum);
    }

}
