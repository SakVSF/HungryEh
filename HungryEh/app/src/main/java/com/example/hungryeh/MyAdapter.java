package com.example.hungryeh;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<stall> list;
    private SelectListener listener;

    public MyAdapter(Context context, ArrayList<stall> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        stall stallh= list.get(position);
        holder.dishName.setText(stallh.getDishName());
        holder.allergy.setText(stallh.getAllergens());
        holder.stall.setText(stallh.getStallName());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(list.get(holder.getAdapterPosition()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView dishName,allergy,stall;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName= itemView.findViewById(R.id.dish);
            allergy= itemView.findViewById(R.id.allergens);
            stall= itemView.findViewById(R.id.stall);
            cardView = itemView.findViewById(R.id.main_container);

        }
    }
}
