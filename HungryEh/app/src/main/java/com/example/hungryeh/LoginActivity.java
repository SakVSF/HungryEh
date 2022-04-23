package com.example.hungryeh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText email;
    private EditText password;
    private TextView textViewLogin;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.button_login);
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        textViewLogin = (TextView)findViewById(R.id.tv_notReg);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == login)
                {
                    signIn();
                }
            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==textViewLogin) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            }
        });
    }
    private void signIn(){
        String U_sername = email.getText().toString().trim();
        String P_assword = password.getText().toString().trim();

        if(TextUtils.isEmpty(U_sername))
        {
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(P_assword))
        {
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(U_sername, P_assword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            /*updateUI(user); */
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                        }

                    }});


    }
}

