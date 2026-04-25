package com.temmahadi.healthcare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temmahadi.healthcare.R;
import com.temmahadi.healthcare.RoomDB.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<CartItem> cartItems;
    private final OnCartItemDeleteListener deleteListener;

    public interface OnCartItemDeleteListener {
        void onDeleteClick(CartItem cartItem);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemDeleteListener deleteListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.packageName.setText(item.packageName);
        holder.cost.setText("৳" + item.cost);

        holder.btnRemove.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, cost;
        ImageView btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.cartPackageName);
            cost = itemView.findViewById(R.id.cartCost);
            btnRemove = itemView.findViewById(R.id.btnRemoveCart);
        }
    }
}
