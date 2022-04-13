package com.example.hungryeh;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartItem> cartItem;


    public CartAdapter(Context context, ArrayList<CartItem> cartItem) {
        this.context = context;
        this.cartItem = cartItem;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartitems, parent, false);
        return new CartViewHolder(v).linkAdapter(this);
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        CartItem fooditem = cartItem.get(position);
        holder.dishName.setText(fooditem.getDishName());
        holder.quantity.setText(String.valueOf(fooditem.getQuantity()));
        holder.orderTime.setText(fooditem.getOrderTime());
        holder.totalPrice.setText(String.format("%.2f",fooditem.getTotalPrice()));

        Glide.with(context).load(fooditem.getImg()).into(holder.foodImg);


    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView quantity;
        TextView totalPrice;
        TextView dishName;
        TextView orderTime;
        ImageView foodImg;
        Button delete;
        private CartAdapter adapter;
        FirebaseFirestore firestore;
        FirebaseAuth auth;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishname);
            quantity = itemView.findViewById(R.id.cartquantity);
            totalPrice = itemView.findViewById(R.id.totalprice);
            orderTime = itemView.findViewById(R.id.timeslot);
            foodImg = itemView.findViewById(R.id.imagecart);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firestore = FirebaseFirestore.getInstance();
                    auth = FirebaseAuth.getInstance();
                    adapter.cartItem.remove(getAdapterPosition());
                    updateCart();
                }
            });
        }

        public void updateCart(){
            firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).collection("Mycart").document(dishName.getText().toString()).delete();
        }

        public CartViewHolder linkAdapter(CartAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
