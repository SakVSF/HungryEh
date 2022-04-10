package com.example.hungryeh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class stall_item_page_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_temp_food_page);

        Intent intent = getIntent();
        stall myStall = intent.getParcelableExtra("myStall_data");
        TextView txtvw_foodpage_dish = findViewById(R.id.foodpage_dish);
        TextView txtvw_foodpage_stall = findViewById(R.id.foodpage_stall);
        txtvw_foodpage_dish.setText(myStall.dishName);
        txtvw_foodpage_stall.setText(myStall.stallName);
    }
}
