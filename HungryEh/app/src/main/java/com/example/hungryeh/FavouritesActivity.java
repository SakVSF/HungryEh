package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {


        RecyclerView recyclerView;
        ArrayList<FavouriteItem> favItem;
        FavouriteAdapter favouriteAdapter;
        FirebaseFirestore firestore;
        FirebaseAuth auth;
        Button gotohome;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_favourites);

            recyclerView = findViewById(R.id.favlist);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            gotohome = findViewById(R.id.gotohomepage );

            firestore = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();
            favItem = new ArrayList<FavouriteItem>();
            favouriteAdapter = new FavouriteAdapter(com.example.hungryeh.FavouritesActivity.this, favItem);

            recyclerView.setAdapter(favouriteAdapter);
            EventChangeListener();

            gotohome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(com.example.hungryeh.FavouritesActivity.this, HomePageActivity.class));
                }
            });
        }


        private void getFavouritesDetails() {
            firestore.collection("favItems").document(auth.getCurrentUser().getUid()).collection("Favourites").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            favItem.add(dc.getDocument().toObject(FavouriteItem.class));
                        }
                    }

                }
            });

        }
        private void EventChangeListener() {
            firestore.collection("favItems").document(auth.getCurrentUser().getUid()).collection("Favourites").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }
                    for(DocumentChange dc: value.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            favItem.add(dc.getDocument().toObject(FavouriteItem.class));
                        }
                        favouriteAdapter.notifyDataSetChanged();
                    }


                }
            });
        }

}

