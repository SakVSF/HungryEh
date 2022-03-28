package com.example.newandroidinfo;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText name;

    private EditText email;
    private EditText password;
    private EditText repassword;
    private TextView tv_notReg;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!=null){
            finish();
           // startActivity(new Intent(getApplication(),Dashboard.class));
        }


        databaseReference =  FirebaseDatabase.getInstance().getReference();

        register = (Button)findViewById(R.id.button_register);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        repassword = (EditText) findViewById(R.id.et_repassword);
        tv_notReg = (TextView) findViewById(R.id.tv_notReg);
        tv_notReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplication(),LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
        //pd = new ProgressDialog(this);
    }
    private void registerUser(){

        String E_mail= email.getText().toString().trim();
        String P_assword = password.getText().toString().trim();
        String Re_password = repassword.getText().toString().trim();

        if(TextUtils.isEmpty(E_mail))
        {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            //finish();
            //startActivity(new Intent(getApplication(),Dashboard.class));
            return;
        }
        if(TextUtils.isEmpty(P_assword))
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Re_password))
        {
            Toast.makeText(this,"Enter Re-password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(Re_password))
        {
            Toast.makeText(this,"Password Does Not Match",Toast.LENGTH_SHORT).show();
            //finish();
            //startActivity(new Intent(getApplication(),Dashboard.class));
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(E_mail,P_assword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Register Success",Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this,"Register Failure",Toast.LENGTH_SHORT).show();
                        }
                    }

                });


    }
}
