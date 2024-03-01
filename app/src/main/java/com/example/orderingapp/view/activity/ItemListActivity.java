package com.example.orderingapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.orderingapp.view.adapter.ItemAdapter;
import com.example.orderingapp.viewmodel.ItemListViewModel;
import com.example.orderingapp.viewmodel.ItemListViewModelFactory;
import com.example.orderingapp.util.callbacks.OnItemUpdateAdapter;
import com.example.orderingapp.R;
import com.example.orderingapp.model.RestaurantMenuItem;
import com.example.orderingapp.databinding.ActivityItemListBinding;
import com.example.orderingapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements OnItemUpdateAdapter {

    private ActivityItemListBinding binding;
    private ItemAdapter itemAdapter;

    private String scannedTableNumber;

    private ItemListViewModel itemListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        setupRecyclerView();
        binding.createButton.setOnClickListener(v -> {
            Intent checkoutActivityIntent = getCheckoutActivityIntent();
            if (isOrderListEmpty(checkoutActivityIntent)) return;
            startActivity(checkoutActivityIntent);
        });
    }

    private void initViewModel() {
        setTableValue();
        setupViewModel();
        observeMenuItems();
        observeItemSum();
    }

    private void observeItemSum() {
        itemListViewModel.getItemSum().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String itemSum) {
                binding.itemSum.setText(itemSum);
            }
        });
    }

    private void observeMenuItems() {
        itemListViewModel.getRestaurantMenuItemsLiveData().observe(this, new Observer<List<RestaurantMenuItem>>() {
            @Override
            public void onChanged(List<RestaurantMenuItem> restaurantMenuItems) {
                itemAdapter.setRestaurantMenuItemList(restaurantMenuItems);            }
        });
    }

    private void setupViewModel() {
        itemAdapter = new ItemAdapter();
        itemAdapter.setOnItemUpdateListener(this);
        itemListViewModel = new ViewModelProvider(this, new ItemListViewModelFactory(Long.valueOf(scannedTableNumber))).get(ItemListViewModel.class);
        itemAdapter.setItemListViewModel(itemListViewModel);
    }
    private void setupRecyclerView() {
        binding = ActivityItemListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(itemAdapter);
        String tableNumberDisplay = getString(R.string.TABLE_TAG) + scannedTableNumber;
        binding.tableNumberTextView.setText(tableNumberDisplay);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ItemListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @NonNull
    private Intent getCheckoutActivityIntent() {
        Intent checkoutActivityIntent = new Intent(ItemListActivity.this, CheckoutActivity.class);
        List<RestaurantMenuItem> restaurantMenuItems = filterItemsWithQuantity(itemAdapter.getItemList(), checkoutActivityIntent);
        checkoutActivityIntent.putParcelableArrayListExtra("restaurantMenuItems", new ArrayList<>(restaurantMenuItems));
        setExtras(checkoutActivityIntent);
        return checkoutActivityIntent;
    }

    private List<RestaurantMenuItem> filterItemsWithQuantity(List<RestaurantMenuItem> itemList, Intent checkoutActivityIntent) {
        List<RestaurantMenuItem> filteredList = new ArrayList<>();
        for (RestaurantMenuItem item : itemList) {
            Long itemId = item.getId();
            int quantity = itemListViewModel.getQuantity(itemId);
            if (quantity > 0) {
                filteredList.add(item);
                checkoutActivityIntent.putExtra(String.valueOf(itemId), quantity);
            }
        }
        return filteredList;
    }

    private void setExtras(Intent checkoutActivityIntent) {
        if(!itemListViewModel.getItemQuantityMap().isEmpty()){
            checkoutActivityIntent.putExtra(Constants.SCANNED_TABLE_NUMBER, scannedTableNumber);
            checkoutActivityIntent.putExtra(Constants.itemSum, binding.itemSum.getText());
        }
    }

    private boolean isOrderListEmpty(Intent checkoutActivityIntent) {
        if (!checkoutActivityIntent.hasExtra(Constants.itemSum)) {
            Toast.makeText(getApplicationContext(), R.string.add_first_warning, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setTableValue() {
        Intent intent = getIntent();
        if (intent.getStringExtra(Constants.SCANNED_TABLE_NUMBER) != null) {
            scannedTableNumber = intent.getStringExtra(Constants.SCANNED_TABLE_NUMBER);
        }
    }

    @Override
    public void onUpdateTotal(String total) {
        if(itemListViewModel != null){
            itemListViewModel.setItemSum(total);
        }
    }
}
