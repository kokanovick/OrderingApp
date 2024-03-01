package com.example.orderingapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.orderingapp.model.OrderRequest;
import com.example.orderingapp.databinding.CheckoutLayoutBinding;
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{

    private final List<OrderRequest> orderRequests;

    public CheckoutAdapter(List<OrderRequest> orderRequests) {
        this.orderRequests = orderRequests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckoutLayoutBinding binding = CheckoutLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderRequest orderRequest = orderRequests.get(position);
        setTableValues(holder, orderRequest);
        setTotalPrice(holder, orderRequest);
    }
    private static void setTotalPrice(@NonNull ViewHolder holder, OrderRequest orderRequest) {
        String totalPrice = String.valueOf(orderRequest.getQuantity() * orderRequest.getPrice());
        holder.binding.totalPrice.setText(totalPrice);
    }

    private static void setTableValues(@NonNull ViewHolder holder, OrderRequest orderRequest) {
        holder.binding.itemName.setText(orderRequest.getName());
        String itemPrice = String.valueOf(orderRequest.getPrice());
        holder.binding.itemPrice.setText(itemPrice);
        holder.binding.quantity.setText(String.valueOf(orderRequest.getQuantity()));
    }
    @Override
    public int getItemCount() {
        return orderRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckoutLayoutBinding binding;

        public ViewHolder(@NonNull CheckoutLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
