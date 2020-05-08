package com.example.farmtoflat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mname, mphone, mflat;
    EditText memail, mpassword, mrepassword;
    Button mcreateAccount;
    TextView existingUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);

        mname = findViewById(R.id.name);
        mphone = findViewById(R.id.mobile);
        mflat = findViewById(R.id.flat);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mrepassword = findViewById(R.id.reenterpassword);
        mcreateAccount = findViewById(R.id.register);
        existingUser = findViewById(R.id.existing_user);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        mcreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String email = memail.getText().toString();
//                String password = mpassword.getText().toString();
//                String flat = mflat.getText().toString();
//                String email = memail.getText().toString();


                if (!mpassword.getText().toString().equals(mrepassword.getText().toString())) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Password do no match", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mname.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Name can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (mflat.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Flat can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (mphone.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "MobileNo. can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (memail.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "email can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (mpassword.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Password can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (mrepassword.getText().toString().isEmpty()) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Re enter password can't be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (mrepassword.getText().toString().length() < 6) {
                    Toast.makeText(com.example.farmtoflat.RegisterActivity.this, "Password should be of 6 characters minimum", Toast.LENGTH_LONG).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(memail.getText().toString(), mpassword.getText().toString())
                            .addOnCompleteListener(com.example.farmtoflat.RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.i("FAILED", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(com.example.farmtoflat.RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
