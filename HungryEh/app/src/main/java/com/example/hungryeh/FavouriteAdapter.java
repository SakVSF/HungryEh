package com.example.hungryeh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    Context context;
    ArrayList<FavouriteItem> favouriteItem;

    public FavouriteAdapter(Context context, ArrayList<FavouriteItem> favouriteItem) {
        this.context = context;
        this.favouriteItem = favouriteItem;
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favouritesitems, parent, false);
        return new FavouriteViewHolder(v).linkAdapter(this);
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

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;

        ImageView foodImg;
        Button remove;
        private FavouriteAdapter favadapter;
        FirebaseFirestore firestore;
        FirebaseAuth auth;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishname);
            foodImg = itemView.findViewById(R.id.imagecart);
            remove = itemView.findViewById(R.id.remove);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firestore = FirebaseFirestore.getInstance();
                    auth = FirebaseAuth.getInstance();
                    favadapter.favouriteItem.remove(getAdapterPosition());
                    updateFav();
                }
            });


        }

        public void updateFav() {
            firestore.collection("favItems").document(auth.getCurrentUser().getUid()).collection("Favourites").document(dishName.getText().toString()).delete();
        }

        public FavouriteViewHolder linkAdapter(FavouriteAdapter favadapter) {
            this.favadapter = favadapter;
            return this;
        }
    }
}


