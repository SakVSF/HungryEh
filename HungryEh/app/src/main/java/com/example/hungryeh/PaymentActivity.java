package com.example.hungryeh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tv_payment_total_cost = findViewById(R.id.tv_payment_total_cost);
        RadioButton rb_paylah = findViewById(R.id.rb_paylah);
        RadioButton rb_creditcard = findViewById(R.id.rb_creditcard);
        RadioGroup rbg_pay_mode = (RadioGroup) findViewById(R.id.rbg_pay_mode);
        LinearLayout linearLayout_paylah = findViewById(R.id.linearLayout_paylah);
        LinearLayout linearLayout_creditcard = findViewById(R.id.linearLayout_creditcard);

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
                }

                RadioButton rb_selected = (RadioButton) rbg_pay_mode.findViewById(checkedId);

                if(rb_selected == rb_paylah){
                    linearLayout_paylah.setVisibility(View.VISIBLE);
                    linearLayout_creditcard.setVisibility(View.GONE);
                }

                if(rb_selected == rb_creditcard){
                    linearLayout_paylah.setVisibility(View.GONE);
                    linearLayout_creditcard.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    // Set phone number

    //Set Credi Card

}

