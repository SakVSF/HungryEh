package com.example.hungryeh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartItem> cartItem;

    public CartAdapter(Context context, ArrayList<CartItem> cartItem) {
        this.context = context;
        this.cartItem = cartItem;
    }

    @NonNull
    @Override
    public com.example.hungryeh.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.hungryeh.MyAdapter.MyViewHolder holder, int position) {

    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
