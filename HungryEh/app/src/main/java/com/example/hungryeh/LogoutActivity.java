package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {
    //activity associated with logout page
    private Button logoutyes;
    private Button logoutno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        logoutyes = (Button) findViewById(R.id.buttonyes);
        logoutno = (Button) findViewById(R.id.buttonno);

        logoutyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==logoutyes){
                    FirebaseAuth.getInstance().signOut();       //activate firebase.signout() if "yes" button is pressed
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //goes to opening page of app 
                }

            }

            });
        logoutno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==logoutno){
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    //goes back to Homepage if "no" button is pressed
                }

            }

        });



    }
}