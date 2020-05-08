package com.example.farmtoflat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView newUser;
    EditText email, password;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        logInButton = findViewById(R.id.login);
        newUser = findViewById(R.id.new_user);

        mAuth = FirebaseAuth.getInstance();

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user.isEmailVerified())
                                        {
                                            Toast.makeText(com.example.farmtoflat.LoginActivity.this, "Logged in",
                                                    Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(com.example.farmtoflat.LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            // user is verified, so you can finish this activity or send user to activity which you want.


                                        }
                                        else
                                        {

                                            // email is not verified, so just prompt the message to the user and restart this activity.
                                            // NOTE: don't forget to log out the user.
                                            Toast.makeText(com.example.farmtoflat.LoginActivity.this,"Email Not Verified or does not exist!!",Toast.LENGTH_LONG).show();
                                            FirebaseAuth.getInstance().signOut();
                                            //restart this activity
                                        }
                                    } else {
                                        Toast.makeText(com.example.farmtoflat.LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                catch (Exception e){
                    if(e.getMessage()=="Given String is empty or null")
                        Toast.makeText(com.example.farmtoflat.LoginActivity.this,"Enter email & password",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(com.example.farmtoflat.LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //checking if the user is already logged in, then log in directly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(com.example.farmtoflat.LoginActivity.this, "Logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(com.example.farmtoflat.LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
