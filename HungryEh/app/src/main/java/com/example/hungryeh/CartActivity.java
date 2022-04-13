package com.example.hungryeh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CartItem> cartItem;
    CartAdapter cartAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        cartItem = new ArrayList<CartItem>();
        cartAdapter = new CartAdapter(CartActivity.this, cartItem);

        recyclerView.setAdapter(cartAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).collection("Mycart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if (error != null) {
                   Log.e("Firestore error", error.getMessage());
                   return;
               }
               for(DocumentChange dc: value.getDocumentChanges()){
                   if (dc.getType() == DocumentChange.Type.ADDED){
                       cartItem.add(dc.getDocument().toObject(CartItem.class));
                   }
                   cartAdapter.notifyDataSetChanged();
               }


            }
        });
    }
}