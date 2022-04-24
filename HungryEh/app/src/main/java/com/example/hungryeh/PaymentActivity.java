package com.example.hungryeh;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    //Variable and fields declaration
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
    TextView tv_creditcard_security;
    EditText et_creditcard_security;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Firestore initialization
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Variable and fields initialization
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
        tv_creditcard_security = findViewById(R.id.tv_creditcard_security);
        et_creditcard_security = findViewById(R.id.et_creditcard_security);

        //Retrieve Database
        getCartDetails();

        // get cartItem details, to calculate total price
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
                    //cartAdapter.notifyDataSetChanged();
                }


            }
        });

        // based on radiobutton selection display correct fields
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


        // radiobutton validation
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
                            et_creditcard_name.setText("");
                            et_creditcard_number.setText("");
                            et_creditcard_security.setText("");
                            submit_order_to_db();
                            startActivity(new Intent(PaymentActivity.this, HomePageActivity.class));
                            updateCart();
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
                        else if( et_creditcard_security.getText().toString().trim().length() < 3){
                            Toast.makeText(getApplicationContext(),"Invalid Credit Card Security Code",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            et_paylah_phone.setText("");
                            submit_order_to_db();
                        }
                    }


                }
            }
        });

    }

    // get latest cartitem details and add to order firestore document
    public void submit_order_to_db(){

        //get latest again
        firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).collection("Mycart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                addToOrderItems(value);
                Toast.makeText(getApplicationContext(),"Payment Success!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext() , HomePageActivity.class);
                startActivity(intent);
            }
        });

    }

    // get latest cartitem details and Initialize payment page with total price to pay
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

    // get field item values and insert into OrderItem firstore document and delete from cartItems
    public void addToOrderItems(@Nullable QuerySnapshot value) {

        Timestamp order_dt = new Timestamp(new Date());

        SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMddHHmmss");
        String firestore_timestamp = sfd.format(order_dt.toDate());

        Map<String, Object> orderReceipt = new HashMap<>();
        orderReceipt.put("overalltotalprice", OverallTotalPrice);
        orderReceipt.put("paylah_phone", et_paylah_phone.getText().toString());
        orderReceipt.put("creditcard_name", et_creditcard_name.getText().toString());
        orderReceipt.put("creditcard_number", et_creditcard_number.getText().toString());
        orderReceipt.put("creditcard_security", et_creditcard_security.getText().toString());
        orderReceipt.put("dateordered", order_dt);

        firestore.collection("orderItems").document(auth.getCurrentUser().getUid()).collection("receipts").document(firestore_timestamp).set(orderReceipt);

        for(DocumentSnapshot doc : value.getDocuments()){
            firestore.collection("orderItems").document(auth.getCurrentUser().getUid()).collection("receipts").document(firestore_timestamp).collection("Mycart").add(doc.getData());
        }

        for(DocumentSnapshot doc : value.getDocuments()){
            firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).collection("Mycart").document(doc.getId()).delete();
        }
    }

    // calculate price to pay vased on items on cart format dollar value
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


    //delete cart items
    public void updateCart(){
        firestore.collection("cartItems").document(auth.getCurrentUser().getUid()).delete();
    }

    //format dollar value
    double RoundTo2Decimals(double val) {
        return Math.round(val*100.0)/100.0;
    }

}

