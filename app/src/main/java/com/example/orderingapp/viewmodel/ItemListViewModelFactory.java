package com.example.orderingapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.orderingapp.viewmodel.ItemListViewModel;

public class ItemListViewModelFactory implements ViewModelProvider.Factory {

    private final Long tableId;

    public ItemListViewModelFactory(Long tableId) {
        this.tableId = tableId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemListViewModel.class)) {
            return (T) new ItemListViewModel(tableId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

