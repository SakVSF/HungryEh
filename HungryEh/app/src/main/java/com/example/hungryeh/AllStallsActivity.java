package com.example.hungryeh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllStallsActivity extends AppCompatActivity {
    DatabaseReference rep;
    ArrayList<Stall> list;
    RecyclerView recyclerView;
    SearchView searchView;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allstalls);
        rep = FirebaseDatabase.getInstance().getReference().child("Stall").child("Menu");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.search);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (rep != null) {
            rep.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(Stall.class));
                        }
                        Adapter adapter = new Adapter(list);
                        recyclerView.setAdapter(adapter);


                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                    Toast.makeText(AllStallsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

        if (searchView!= null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }

    }
    private void search(String s){
        ArrayList<Stall> myStall= new ArrayList<>();
        for (Stall object:list){
            if(object.getAllergens().toLowerCase().contains(s.toLowerCase())){
                myStall.add(object);


            }
            Adapter adapter = new Adapter(myStall);
            recyclerView.setAdapter(adapter);


        }
    }
}


class Stall {
    private String dishName;
    private String stallName;
    private String allergens;
    private String veg;
    private String price;

    public Stall() {
    }

    public Stall(String dishName, String stallName, String allergens, String veg, String price) {
        this.dishName = dishName;
        this.stallName = stallName;
        this.allergens = allergens;
        this.veg = veg;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
