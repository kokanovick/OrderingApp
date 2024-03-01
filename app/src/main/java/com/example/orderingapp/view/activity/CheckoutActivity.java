package com.example.orderingapp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.example.orderingapp.view.adapter.CheckoutAdapter;
import com.example.orderingapp.util.timer.ITimerTaskEvents;
import com.example.orderingapp.util.callbacks.OnOrderReceivedCallback;
import com.example.orderingapp.util.timer.OrderTimerTask;
import com.example.orderingapp.DAO.PostRequest;
import com.example.orderingapp.R;
import com.example.orderingapp.model.FinalizedOrder;
import com.example.orderingapp.model.OrderRequest;
import com.example.orderingapp.model.OrderResponse;
import com.example.orderingapp.model.RestaurantMenuItem;
import com.example.orderingapp.databinding.CheckoutItemListBinding;
import com.example.orderingapp.DAO.RealRepository;
import com.example.orderingapp.repository.Repository;
import com.example.orderingapp.util.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class CheckoutActivity extends AppCompatActivity implements ITimerTaskEvents {

    private CheckoutItemListBinding binding;
    private String scannedTableNumber;
    List<OrderRequest> orderRequests;
    OrderResponse response;
    private OrderTimerTask orderTimerTask;
    private Timer timer;
    private Vibrator vibrator;
    private boolean vibratedAlready = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRestaurantMenuItemList();
        PostRequest postRequest = createPostRequest();
        binding.confirmOrder.setOnClickListener(v -> {
            postRequest.setComment(binding.addComment.getText().toString());
            sendData(postRequest);
            setupStatusUI();
        });
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void setRestaurantMenuItemList() {
        binding = CheckoutItemListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<RestaurantMenuItem> menuItems = getIntent().getParcelableArrayListExtra("restaurantMenuItems");
        extractOrderRequests(menuItems);
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(orderRequests);
        binding.checkoutRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        binding.checkoutRecylcerView.setAdapter(checkoutAdapter);
        setTableNumber(getIntent().getStringExtra(Constants.SCANNED_TABLE_NUMBER));
        setSumPrice(getIntent().getStringExtra(Constants.itemSum));
    }

    private void extractOrderRequests(List<RestaurantMenuItem> menuItems) {
        orderRequests = new ArrayList<>(0);
        for (RestaurantMenuItem restaurantMenuItem : menuItems) {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setName(restaurantMenuItem.getName());
            orderRequest.setQuantity(getIntent().getIntExtra(String.valueOf(restaurantMenuItem.getId()), 0));
            orderRequest.setDrinkId(restaurantMenuItem.getId());
            orderRequest.setPrice(restaurantMenuItem.getPrice());
            orderRequests.add(orderRequest);
        }
    }

    private void sendData(PostRequest postRequest) {
        Repository repository = new RealRepository();
        repository.makeOrder(postRequest, new OnOrderReceivedCallback() {
            @Override
            public void onOrderReceived(OrderResponse orderResponse) {
                Log.d("Success", "Success");
                response = orderResponse;
                createTimerTask(response);
            }
        });
    }

    private void createTimerTask(OrderResponse response) {
        orderTimerTask = new OrderTimerTask(response);
        orderTimerTask.setTimerTaskEventsListener(this);
        timer = new Timer();
        timer.scheduleAtFixedRate(orderTimerTask, 0, 5000);
    }

    private PostRequest createPostRequest() {
        return new PostRequest(Long.valueOf(scannedTableNumber), orderRequests);
    }

    private void setTableNumber(String tableNumber) {
        scannedTableNumber = tableNumber;
    }

    private void setSumPrice(String itemSum) {
        String sumValue = getString(R.string.total) + itemSum;
        binding.sumPrice.setText(sumValue);
    }

    @Override
    public void onTimerTaskEvent(FinalizedOrder finalizedOrder) {
        Log.d(Constants.CHECKOUT_ACTIVITY, "onTimerTaskEvent: finalizedOrder: " + finalizedOrder);
        binding.orderIdTextView.setText(getString(R.string.order_id, String.valueOf(finalizedOrder.getId())));
        long[] pattern = definePattern();
        if(finalizedOrder.getStatus().equals("READY") && !vibratedAlready){
            binding.orderStatusText.setText(getString(R.string.order_started));
            vibratePhone(pattern);
            vibratedAlready = true;
        }
        else if(finalizedOrder.getStatus().equals("completed")){
            binding.orderStatusText.setText(getString(R.string.order_completed));
            binding.orderStatusText.setTextColor(Color.RED);
            orderTimerTask.setTimerTaskEventsListener(null);
            timer.cancel();
            vibratePhone(pattern);
        }
    }

    @NonNull
    private static long[] definePattern() {
        int strong_vibration = 1000, pause = 500;
        return new long[]{0, strong_vibration, pause};
    }

    private void setupStatusUI() {
        binding.addComment.setVisibility(View.GONE);
        binding.sumPrice.setVisibility(View.GONE);
        binding.confirmOrder.setVisibility(View.GONE);
        binding.orderIdTextView.setVisibility(View.VISIBLE);
        binding.orderStatusText.setVisibility(View.VISIBLE);
        binding.orderStatusText.setText(R.string.order_new);
    }

    public void vibratePhone(long[] pattern){
        vibrator.vibrate(pattern, -1);
    }
}
