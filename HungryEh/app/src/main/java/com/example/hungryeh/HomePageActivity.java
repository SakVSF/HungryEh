package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomePageActivity extends AppCompatActivity {
    GridLayout mainGridLayout;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mainGridLayout = (GridLayout) findViewById(R.id.maing);
        setSingleEvent(mainGridLayout);

    }

    private void setSingleEvent(GridLayout mainGridLayout) {
        for (int i = 0; i < mainGridLayout.getChildCount(); i++) {
            CardView cardview = (CardView) mainGridLayout.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0) {
                        Intent intent = new Intent(HomePageActivity.this, AllStallsActivity.class);
                        startActivity(intent);
                    } else if (finalI == 1) {
                        Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else if (finalI == 2) {
                        Intent intent = new Intent(HomePageActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                    } else if (finalI == 3) {
                        Intent intent = new Intent(HomePageActivity.this, MyOrdersActivity.class);
                        startActivity(intent);
                    } else if (finalI == 4) {
                        Intent intent = new Intent(HomePageActivity.this, PaymentActivity.class);
                        startActivity(intent);
                    } else if (finalI == 5) {
                        Intent intent = new Intent(HomePageActivity.this, TimingsActivity.class);
                        startActivity(intent);
                    }


                }
            });
        }
    }
}