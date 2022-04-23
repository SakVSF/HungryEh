package com.example.hungryeh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    Button paynow;
    private double OverallTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paynow = findViewById(R.id.paynow);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        cartItem = new ArrayList<CartItem>();
        cartAdapter = new CartAdapter(CartActivity.this, cartItem); //initializing cartadapter for recyclerview

        recyclerView.setAdapter(cartAdapter);
        EventChangeListener(); //actively checking for changes in the firebase to add items into the cartview


        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //payment button leading to the payment page
                startActivity(new Intent(CartActivity.this, PaymentActivity.class));
            }
        });
    }

    private void initializePayment() {
        // to display cart total on the payment button on cart
        OverallTotalPrice = 0;
        for ( CartItem item : cartItem ) {
            OverallTotalPrice += item.totalprice;
        }
        paynow.setText("Pay $"+String.format("%.2f",OverallTotalPrice) + " Now");
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
                       cartItem.add(dc.getDocument().toObject(CartItem.class)); //adding items to arraylist whenever items are added into cart database
                   }
                   cartAdapter.notifyDataSetChanged();//notifying change whenever items are added into the cart database

               }
               initializePayment(); //recalculates total everytime EventChangeListener is called

            }
        });
    }
}