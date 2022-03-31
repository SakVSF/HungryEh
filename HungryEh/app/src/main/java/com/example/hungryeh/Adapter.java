package com.example.hungryeh;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHold>{
    ArrayList<Stall> stalls;

    public Adapter(ArrayList<Stall> stall) {
        this.stalls= stall;

    }
    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_hold,parent,false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        holder.stallsid.setText(stalls.get(position).getStallName());

    }

    @Override
    public int getItemCount() {
        return stalls.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{
        TextView stallsid,allg,veggie;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            stallsid= itemView.findViewById(R.id.stallid);
            allg= itemView.findViewById(R.id.allergy);
            veggie= itemView.findViewById(R.id.veg);

        }
    }


}