package com.example.hungryeh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ArrayList<CartItem> cartItem;
    double OverallTotalPrice;
    TextView tv_payment_total_cost;
    TextView tv_payment_total_cost_value;
    RadioButton rb_paylah ;
    RadioButton rb_creditcard ;
    RadioGroup rbg_pay_mode ;
    LinearLayout linearLayout_paylah ;
    LinearLayout linearLayout_creditcard ;
    Button btn_payment_order;

    TextView tv_paylah_phone;
    EditText et_paylah_phone;
    TextView tv_creditcard_name;
    EditText et_creditcard_name;
    TextView tv_creditcard_number;
    EditText et_creditcard_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        cartItem = new ArrayList<CartItem>();

        tv_payment_total_cost = findViewById(R.id.tv_payment_total_cost);
        tv_payment_total_cost_value = findViewById(R.id.tv_payment_total_cost_value);
        rb_paylah = findViewById(R.id.rb_paylah);
        rb_creditcard = findViewById(R.id.rb_creditcard);
        rbg_pay_mode = (RadioGroup) findViewById(R.id.rbg_pay_mode);
        linearLayout_paylah = findViewById(R.id.linearLayout_paylah);
        linearLayout_creditcard = findViewById(R.id.linearLayout_creditcard);
        btn_payment_order = findViewById(R.id.btn_payment_order);

        tv_paylah_phone = findViewById(R.id.tv_paylah_phone);
        et_paylah_phone = findViewById(R.id.et_paylah_phone);
        tv_creditcard_name = findViewById(R.id.tv_creditcard_name);
        et_creditcard_name = findViewById(R.id.et_creditcard_name);
        tv_creditcard_number = findViewById(R.id.tv_creditcard_number);
        et_creditcard_number = findViewById(R.id.et_creditcard_number);

        //Retrieve Database
        getCartDetails();


        rbg_pay_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (rbg_pay_mode.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    linearLayout_paylah.setVisibility(View.GONE);
                    linearLayout_creditcard.setVisibility(View.GONE);
                    btn_payment_order.setEnabled(false);
                }

                RadioButton rb_selected = (RadioButton) rbg_pay_mode.findViewById(checkedId);

                if(rb_selected == rb_paylah){
                    linearLayout_paylah.setVisibility(View.VISIBLE);
                    linearLayout_creditcard.setVisibility(View.GONE);
                    btn_payment_order.setEnabled(true);
                }

                if(rb_selected == rb_creditcard){
                    linearLayout_paylah.setVisibility(View.GONE);
                    linearLayout_creditcard.setVisibility(View.VISIBLE);
                    btn_payment_order.setEnabled(true);
                }

            }
        });


//        linearLayout_paylah.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    if (et_paylah_phone.getText().toString().trim().length() < 5) {
//                        et_paylah_phone.setError("Failed");
//                    } else {
//                        // your code here
//                        et_paylah_phone.setError(null);
//                    }
//                } else {
//                    if (et_billamt.getText().toString().trim().length() < 5) {
//                        et_billamt.setError("Failed");
//                    } else {
//                        // your code here
//                        et_billamt.setError(null);
//                    }
//                }
//
//            }
//        });

        btn_payment_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_payment_order.isEnabled()){


                    if(rb_paylah.isChecked()){

                        //check if phone is valid number of digits

                        if( et_paylah_phone.getText().toString().trim().length() < 8){
                            Toast.makeText(getApplicationContext(),"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                        }
//                        else if( !"8".equals(et_paylah_phone.getText().toString().charAt(0).toString()) && !"9".equals(et_paylah_phone.getText().toString().charAt(0)) ){
//                            Toast.makeText(getApplicationContext(),"Invalid Phone Number",Toast.LENGTH_SHORT).show();
//                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(rb_creditcard.isChecked()){
                        //add creditcard validation logic
                        if( et_creditcard_name.getText().toString().trim().length() < 1){
                            Toast.makeText(getApplicationContext(),"Invalid Credit Holder Name",Toast.LENGTH_SHORT).show();
                        }
                        else if( et_creditcard_number.getText().toString().trim().length() < 16){
                            Toast.makeText(getApplicationContext(),"Invalid Credit Card Number",Toast.LENGTH_SHORT).show();
                        }
                        else if( et_creditcard_number.getText().toString().trim().length() < 3){
                            Toast.makeText(getApplicationContext(),"Invalid Credit Card Security Code",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

    }

    private void getCartDetails(){
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
                }
                initializePaymentPage();

            }
        });

    }

    private void initializePaymentPage() {
        //logic to see if there is any cartitem

        //if cartitem has 1 or more item then calculate total
        OverallTotalPrice = 0;
        for ( CartItem item : cartItem ) {
            OverallTotalPrice += item.totalprice;
        }
        OverallTotalPrice = RoundTo2Decimals(OverallTotalPrice);
        tv_payment_total_cost_value.setText(String.format("%.2f",OverallTotalPrice));
    }

    // Set phone number

    //Set Credi Card


    double RoundTo2Decimals(double val) {
//        DecimalFormat df2 = new DecimalFormat("###.##");
//        return Double.valueOf(df2.format(val));
        return Math.round(val*100.0)/100.0;
    }

}

