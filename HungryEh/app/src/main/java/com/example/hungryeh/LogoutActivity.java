package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {

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
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }

            });
        logoutno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==logoutno){
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                }

            }

        });



    }
}