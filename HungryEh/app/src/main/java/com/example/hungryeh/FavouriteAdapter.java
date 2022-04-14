package com.example.hungryeh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    Context context;
    ArrayList<FavouriteItem> favouriteItem;

    public FavouriteAdapter(Context context, ArrayList<FavouriteItem> favouriteItem) {
        this.context = context;
        this.favouriteItem= favouriteItem;
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartitems, parent, false);
        return new FavouriteViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return favouriteItem.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        FavouriteItem fooditem = favouriteItem.get(position);
        holder.dishName.setText(fooditem.getDishName());


        Glide.with(context).load(fooditem.getImg()).into(holder.foodImg);


    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{

        TextView dishName;

        ImageView foodImg;
        ImageButton delete;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishname);
            foodImg = itemView.findViewById(R.id.imagecart);



        }
    }
}

