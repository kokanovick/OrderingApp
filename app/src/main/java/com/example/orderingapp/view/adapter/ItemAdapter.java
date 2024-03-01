package com.example.orderingapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.viewmodel.ItemListViewModel;
import com.example.orderingapp.util.callbacks.OnItemUpdateAdapter;
import com.example.orderingapp.model.RestaurantMenuItem;
import com.example.orderingapp.databinding.ItemLayoutBinding;
import com.example.orderingapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<RestaurantMenuItem> restaurantMenuItemList;
    private OnItemUpdateAdapter onItemUpdateAdapter;
    private ItemListViewModel itemListViewModel;

    public ItemAdapter(List<RestaurantMenuItem> restaurantMenuItemList, OnItemUpdateAdapter onItemUpdateAdapter, ItemListViewModel itemListViewModel) {
        this.restaurantMenuItemList = restaurantMenuItemList;
        this.onItemUpdateAdapter = onItemUpdateAdapter;
        this.itemListViewModel = itemListViewModel;
    }

    public ItemAdapter(){
        this.restaurantMenuItemList = new ArrayList<>();
    }

    public void setOnItemUpdateListener(OnItemUpdateAdapter onItemUpdateAdapter){
        this.onItemUpdateAdapter = onItemUpdateAdapter;
    }

    public void setItemListViewModel(ItemListViewModel itemListViewModel){
        this.itemListViewModel = itemListViewModel;
    }

    public List<RestaurantMenuItem> getItemList(){
        return restaurantMenuItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setItemValues(holder, position);
        incrementQuantity(holder);
        decrementQuantity(holder);
    }

    private void setItemValues(@NonNull ViewHolder holder, int position) {
        RestaurantMenuItem item = restaurantMenuItemList.get(position);
        holder.binding.itemNameTextView.setText(item.getName());
        int quantity = itemListViewModel.getQuantity(item.getId());
        holder.binding.quantityTextView.setText(String.valueOf(quantity));
        String priceLayout = item.getPrice() + Constants.EURO;
        holder.binding.itemPriceTextView.setText(priceLayout);
    }

    public void setRestaurantMenuItemList(List<RestaurantMenuItem> restaurantMenuItemList){
        this.restaurantMenuItemList = restaurantMenuItemList;
        notifyDataSetChanged();
    }
    private void decrementQuantity(@NonNull ViewHolder holder) {
        holder.binding.decrementButton.setOnClickListener(v -> {
            manipulateValue(holder, true);
        });
    }

    private void manipulateValue(@NonNull ViewHolder holder, boolean isDecrement) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            setAmount(isDecrement, adapterPosition, restaurantMenuItemList.get(adapterPosition));
        }
    }

    private void incrementQuantity(@NonNull ViewHolder holder) {
        holder.binding.addButton.setOnClickListener(v -> {
            manipulateValue(holder, false);
        });
    }
    private void setAmount(boolean isDecrement, int adapterPosition, RestaurantMenuItem clickedItem) {
        if (isDecrement) {
            decrement(adapterPosition, clickedItem);
        } else {
            increment(adapterPosition, clickedItem);
        }
        updateTotal();
    }

    private void updateQuantity(int adapterPosition, int multiplier, RestaurantMenuItem clickedItem) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            int currentQuantity = itemListViewModel.getQuantity(clickedItem.getId());
            int updatedValue = currentQuantity + multiplier;
            if (updatedValue >= 0) {
                itemListViewModel.setQuantity(clickedItem.getId(), updatedValue);
                itemListViewModel.getRestaurantMenuItemsLiveData().setValue(itemListViewModel.getRestaurantMenuItemsLiveData().getValue());
            }
        }
    }

    private void decrement(int adapterPosition, RestaurantMenuItem clickedItem) {
        updateQuantity(adapterPosition, -1, clickedItem);
    }

    private void increment(int adapterPosition, RestaurantMenuItem clickedItem) {
        updateQuantity(adapterPosition, 1, clickedItem);
    }


    private void updateTotal() {
        double totalQuantity = 0;
        for (RestaurantMenuItem item : restaurantMenuItemList) {
            int itemQuantity = itemListViewModel.getQuantity(item.getId());
            totalQuantity += itemQuantity * item.getPrice();
        }
        onItemUpdateAdapter.onUpdateTotal(totalQuantity + Constants.EURO);
    }

    @Override
    public int getItemCount() {
        return restaurantMenuItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutBinding binding;

        public ViewHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
