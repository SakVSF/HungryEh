package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AllStallsActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    DatabaseReference ref;
    MyAdapter adapter;
    ArrayList<stall> stallList;
    SearchView searchView;
    StorageReference rep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allstalls);
        recyclerView= findViewById(R.id.menulist);
        ref= FirebaseDatabase.getInstance().getReference("Menu");
        rep= FirebaseStorage.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stallList= new ArrayList<>();
        adapter= new MyAdapter(this, stallList,this );
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//get data inside stall object
                for(DataSnapshot dataSnapshot: snapshot.getChildren() ){
                    stall stalls= dataSnapshot.getValue(stall.class);
                    stallList.add(stalls);


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    private void search(String str){
        ArrayList<stall> myStall= new ArrayList<>();
        for (stall object:stallList){
            if(object.getAllergens().toLowerCase().contains(str.toLowerCase())||object.getStallName().toLowerCase().contains(str.toLowerCase())||object.getDishName().toLowerCase().contains(str.toLowerCase())||object.getVeg().toLowerCase().contains(str.toLowerCase())){
                myStall.add(object);

            }



        }
        adapter= new MyAdapter(this, myStall, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(stall myStall) {
        //Toast.makeText(this, "testing - " + myStall.getDishName(), Toast.LENGTH_LONG).show();

        //explicit intent to foodPreview
        Intent intent = new Intent(AllStallsActivity.this , stall_item_page_activity.class);
        intent.putExtra("myStall_data",myStall);
        startActivity(intent);

    }
}




