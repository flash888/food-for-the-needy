package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText name,email,pass,phone;
    Button register;
    TextView loginbtn;
    FirebaseAuth fauth;
    ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.FullName);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password1);
        phone=findViewById(R.id.Phone);
        register=findViewById(R.id.button);
        loginbtn=findViewById(R.id.textView3);
        pbar=findViewById(R.id.progressBar);
        fauth=FirebaseAuth.getInstance();
        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password=pass.getText().toString().trim();
                String Email=email.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    pass.setError("Password is required");
                    return;
                }
                if (Password.length()<8){
                    pass.setError("Passeord must be atleast 8 characters");
                    return;
                }
                pbar.setVisibility(View.VISIBLE);
                fauth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Created Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            SplashScreen.val=true;
                        }
                        else{
                            pbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}