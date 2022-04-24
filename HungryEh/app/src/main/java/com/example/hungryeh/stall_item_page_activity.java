package com.example.hungryeh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class stall_item_page_activity extends AppCompatActivity implements OnDataPass {
    TextView txtvw_quantity;
    int txtvw_totalquantity = 1;
    stall myStall;
    String str_TimeslotSelection;
    TextView textView;
    NumberPicker numberPicker;
    TextView txtvw_schedule_selection;
    TextView addtofav;
    View addToCart;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    double txtvw_realprice;
    LinearLayout ll_preorder_time_linear_layout;

    public stall_item_page_activity(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_food_page);
        Intent intent = getIntent();

        //firebase initialization
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //initialize shared pref
        initalizeIntent();

        //Ui fields initialization
        TextView txtvw_foodpage_dish = findViewById(R.id.foodpage_dish);
        TextView txtvw_foodpage_stall = findViewById(R.id.foodpage_stall);
        TextView txtvw_foodpage_price= findViewById(R.id.cost);
        TextView txtvw_foodpage_allergens = findViewById(R.id.ind_allergens);
        TextView txtvw_foodpage_veg = findViewById(R.id.ind_veg);
        TextView txtvw_addtofav = findViewById(R.id.favtext);
        Button txtvw_addtocart = findViewById(R.id.addtocartbutton);
        Button txtvw_increasequantity = findViewById(R.id.increasequantity);
        Button txtvw_decreasequantity = findViewById(R.id.reducequantity);
        txtvw_quantity = findViewById(R.id.quantity);
        txtvw_schedule_selection = findViewById(R.id.schedule_selection);
        txtvw_realprice = this.myStall.pricen;
        ll_preorder_time_linear_layout = findViewById(R.id.preorder_time_linear_layout);

        ImageView img_foodpage_image=findViewById(R.id.imagein);
        Glide.with(stall_item_page_activity.this)
                .load(this.myStall.img)
                .centerCrop()
                .into(img_foodpage_image);

        txtvw_foodpage_dish.setText(this.myStall.dishName);
        txtvw_foodpage_stall.setText(this.myStall.stallName);
        txtvw_foodpage_price.setText(this.myStall.price);
        txtvw_foodpage_allergens.setText(this.myStall.allergens);
        txtvw_foodpage_veg.setText(this.myStall.veg);

        // addToFav button
        txtvw_addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoFav();
            }
        });

        // Check if Time is selected
        txtvw_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!("Select a time".equals(txtvw_schedule_selection.getText().toString()))){
                    addToCart();
                }
                else{
                    Toast.makeText(stall_item_page_activity.this, "Please Schedule a time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Check if quantiy is valid
        txtvw_increasequantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtvw_totalquantity<10){
                    txtvw_totalquantity++;
                    txtvw_quantity.setText(String.valueOf(txtvw_totalquantity));
                }
            }
        });

        // Check if quantiy is valid
        txtvw_decreasequantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtvw_totalquantity>1){
                    txtvw_totalquantity--;
                    txtvw_quantity.setText(String.valueOf(txtvw_totalquantity));
                }
            }
        });

        // Check if quantiy is valid
        ll_preorder_time_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();}

        }
        );

        // Open Timeslot Schedule Dialog pop up
        txtvw_schedule_selection.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  openDialog();}

            }
        );




    }

    // Add to fav
    private void addtoFav() {
        final HashMap<String, Object> favMap = new HashMap<>();
        favMap.put("dishName",this.myStall.dishName);

        favMap.put("img", this.myStall.img);

        //db.collection('users').doc(user_id).set(cartmap)
        firestore.collection("favItems").document(auth.getCurrentUser().getUid()).collection("Favourites").document(this.myStall.dishName).set(favMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(stall_item_page_activity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Add to cart
    private void addToCart() {
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("dishName",this.myStall.dishName);
        cartMap.put("totalprice", txtvw_totalquantity*txtvw_realprice);
        cartMap.put("quantity", txtvw_totalquantity);
        cartMap.put("orderTime", txtvw_schedule_selection.getText());
        cartMap.put("img", this.myStall.img);

        //db.collection('users').doc(user_id).set(cartmap)
            firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).collection("Mycart").document(this.myStall.dishName).set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(stall_item_page_activity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(stall_item_page_activity.this, HomePageActivity.class));
                }
            });
    }

    //check if paracable object exist, if so cache it & display it, else retrieve from cache
    private void initalizeIntent() {
        // Check if existing data object is in cache
        SharedPreferences sharedPreferences = getSharedPreferences("myStall", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Check if intent has data
        Intent intent = getIntent();
        stall myStall_data = intent.getParcelableExtra("myStall_data");
        // If intent has data, then store into cache
        if( myStall_data != null ) {
            this.myStall = myStall_data;
            Gson gson = new Gson();
            String strobj_myStall = gson.toJson(myStall_data);
            editor.putString("strobj_myStall",strobj_myStall);
            editor.apply();
        }
        // If intent does not have data, then retrieve data from cache
        else {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("strobj_myStall",null);
            Type type = new TypeToken<stall>() {}.getType();
            if (json != null & json != "" )
            {
                stall newStall = gson.fromJson(json, type);
                this.myStall = newStall;
            }
        }

        // Get timeslotselection intent from dialog
        String str_TimeslotSelection_value = intent.getStringExtra("str_TimeslotSelection");
        if( str_TimeslotSelection_value != null & str_TimeslotSelection_value != ""){
            this.str_TimeslotSelection = str_TimeslotSelection_value;
            txtvw_schedule_selection = findViewById(R.id.schedule_selection);
            txtvw_schedule_selection.setText(str_TimeslotSelection);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void openDialog(){
        TimeslotDialog timeslotDialog = new TimeslotDialog();
        //todo get available timeslot for the day
        timeslotDialog.show(getSupportFragmentManager(),"Timeslot Dialog");
    }

    // Get timeslotselection data from dialog using onDataPass Interface
    @Override
    public void onDataPass(String data) {
        String str_TimeslotSelection_value = data;
        if( str_TimeslotSelection_value != null & str_TimeslotSelection_value != ""){
            this.str_TimeslotSelection = str_TimeslotSelection_value;
            txtvw_schedule_selection = findViewById(R.id.schedule_selection);
            txtvw_schedule_selection.setText(str_TimeslotSelection);
            //Toast.makeText(this, "testing - " + str_TimeslotSelection, Toast.LENGTH_SHORT).show();
        }
    }
}
