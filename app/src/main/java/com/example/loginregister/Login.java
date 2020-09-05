package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText email1,password;
    Button loginbtn;
    ProgressBar pbar;
    FirebaseAuth fauth;
    TextView newuser;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final boolean[] val = {false};
        email1=findViewById(R.id.email);
        password=findViewById(R.id.password1);
        loginbtn=findViewById(R.id.Login);
        pbar=findViewById(R.id.progressBar2);
        newuser=findViewById(R.id.textView5);
        remember=findViewById(R.id.remember);
        fauth=FirebaseAuth.getInstance();

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
       String checkbox= preferences.getString("remember","");
       if(checkbox.equals("true"))
       {
         //  Toast.makeText(Login.this, "Login Credentials Unsaved", Toast.LENGTH_SHORT).show();
           startActivity(new Intent(getApplicationContext(),dashboard.class));
            finish();
       }
       else if(checkbox.equals("false"))
       {
           Toast.makeText(Login.this, "Please sign in", Toast.LENGTH_SHORT).show();
       }

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Password=password.getText().toString().trim();
                final String Email=email1.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    email1.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    password.setError("Password is required");
                    return;
                }
                if (Password.length()<8){
                    password.setError("Passeord must be atleast 8 characters");
                    return;
                }
                pbar.setVisibility(View.VISIBLE);

                fauth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            SplashScreen.val=true;
                            pbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            val[0] =true;
                            startActivity(new Intent(getApplicationContext(), dashboard.class));
                            finish();

                        }
                        else{
                            Toast.makeText(Login.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.INVISIBLE);


                        }
                    }
                });

            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked() )
                {
                    SharedPreferences sp=getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("remember","true");
                    Ed.apply();
                   // Toast.makeText(Login.this, "Login Credentials saved", Toast.LENGTH_SHORT).show();

                }
                else {
                    SharedPreferences sp=getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("remember","false");
                    Ed.apply();
                   // Toast.makeText(Login.this, "Login Credentials Unsaved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}