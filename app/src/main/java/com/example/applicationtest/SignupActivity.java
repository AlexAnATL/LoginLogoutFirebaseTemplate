package com.example.applicationtest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initButtons();
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }
    public void initButtons() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //Register button initiation
        Button buttonRegister = this.findViewById(R.id.buttonRegister);
        EditText editTextRegisterEmail = this.findViewById(R.id.editTextRegisterEmail);
        EditText editTextRegisterPassword = this.findViewById(R.id.editTextRegisterPassword);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextRegisterEmail.getText().toString().trim();
                String password = editTextRegisterPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    editTextRegisterEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    editTextRegisterPassword.setError("password is required");
                    return;
                }
                if(password.length() < 6) {
                    editTextRegisterPassword.setError("password must be greater than six characters");
                    return;
                }
                if(password.length() < 6) {
                    editTextRegisterPassword.setError("password must be longer than five characters");
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User Created.", Toast.LENGTH_SHORT ).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Error!" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
}